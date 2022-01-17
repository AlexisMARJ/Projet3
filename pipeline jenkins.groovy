pipeline {
    agent none
    tools {
        maven 'mvn_3.8.4'
        jdk 'jdk_8'
    }      
    stages {
        stage('Build differents version Shopizer') {
            parallel {
                stage('Build Shopizer 2.7.0'){
                    agent {
                        label "built-in"
                    }
                    stages{
                        stage('Nettoyage du Tomcat') {  
                            steps {
                                sh'''
                                if [ -d apache-tomcat-8.5.73 ]
                                then rm -rf apache-tomcat-8.5.73 && rm apache-tomcat-8.5.73.tar
                                else echo 'pas de Nettoyage'
                                fi
                                echo fin du Nettoyage
                                '''
                            }
                        }
                        stage('pull de shopizer 2.7.0') {
                            steps {
                                git branch: '$VERSION', url: 'https://github.com/shopizer-ecommerce/shopizer.git'
                            }
                        }
                        stage('Modification de BDD') {
                            steps {
                                sh'''
                                wget http://alexismarjollet.ddns.net:8081/repository/Tools/database/database.properties
                                wget http://alexismarjollet.ddns.net:8081/repository/Tools/database/shopizer-properties.properties
                                mv database.properties sm-shop/src/main/resources/database.properties
                                mv shopizer-properties.properties sm-shop/src/main/resources/shopizer-properties.properties
                                sed -i 's/<mysql-jdbc-version>5.1.47/<mysql-jdbc-version>5.1.38/g' pom.xml
                                '''
                            }
                        }
                        stage('Build mvn shopizer 2.7.0'){
                            steps {
                                sh 'mvn clean install'
                            }
                        }
                        stage('download tomcat') {
                            steps {
                                sh'wget http://alexismarjollet.ddns.net:8081/repository/Tools/tomcat_8/apache-tomcat-8.5.73.tar'
                            }
                        }
                        stage('install tomcat') {
                            steps {
                                sh'tar -xvf apache-tomcat-8.5.73.tar'
                            }
                        }
                        stage('deplacer ROOT.war') {
                            steps {
                                sh''' 
                                rm -r apache-tomcat-8.5.73/webapps/ROOT*
                                cp sm-shop/target/ROOT.war apache-tomcat-8.5.73/webapps/
                                sed -i \'312a\\export JAVA_OPTS="$JAVA_OPTS -javaagent:\\/home\\/projet3\\/jacoco-0.8.3\\/lib\\/jacocoagent.jar=destfile=\\/home/projet3\\/jacoco-it.exec,append=false"\\n\' apache-tomcat-8.5.73/bin/catalina.sh
                                '''
                            }
                        }
                        stage('Lancement Shopizer') {
                            steps {
                                sh 'JENKINS_NODE_COOKIE=dontKillMe apache-tomcat-8.5.73/bin/startup.sh'
                    
                            }   
                        }
                        stage('Attente du lancement de Tomcat'){
                            steps{
                                sleep time: 5, unit: 'MINUTES'
                            }
                        }
                    }
                }
                stage('Build Shopizer 2.6.0'){
                    agent {
                        label "AgentOlga"
                    }
                    stages{
                        stage('pull de shopizer 2.6.0') {
                            steps {
                                git branch: '$VERSIONAVANT', url: 'https://github.com/shopizer-ecommerce/shopizer.git'
                            }
                        }
                        stage('Build mvn et sonnar de shopizer 2.6.0') {
                            steps {
                                bat 'mvn clean install sonar:sonar'
                            }
                        }
                    }
                }
                stage('Build Shopizer 2.5.0'){
                    agent {
                        label "AgentAlexis"
                    }
                    stages{
                        stage('pull de shopizer 2.5.0') {
                            steps {
                                git branch: '$VERSIONAVANTAVANT', url: 'https://github.com/shopizer-ecommerce/shopizer.git'
                            }
                        }
                        stage('Build mvn et sonnar de shopizer 2.5.0') {
                            steps {
                                bat 'mvn clean install sonar:sonar'
                            }
                        }
                    }
                }
            }
        }
        stage('pull github test selenium') {
            parallel {
                stage('Test Selenium Categories'){
                    agent {
                        label "AgentKim"
                    }
                    stages{
                        stage ('pull test shopizer categories') {
                            steps {
                                git 'https://github.com/ClarisseKim/autom7Shopizer.git'
                            }
                        }
                        stage ('test chrome shopizer categories') {
                            steps {
                                bat 'mvn clean test -Dbrowser="Chrome"'
                            }
                        }
                        stage ('test firefox shopizer categories') {
                            steps {
                                bat 'mvn clean test -Dbrowser="Firefox"'
                            }
                        }
                        stage ('test edge shopizer categories') {
                            steps {
                                bat 'mvn clean test -Dbrowser="Edge"'
                            }
                        }
                    }
                }
                stage('Test Selenium panier'){
                    agent {
                        label "AgentOlga"
                    }
                    stages{
                        stage ('pull test shopizer panier') {  
                            steps {
                                git branch: 'main', url: 'https://github.com/OlgaBou/shopizer.git'
                            }
                        }
                        stage ('test chrome shopizer panier') {
                            steps {
                                bat 'mvn clean test -DNavigateur="Chrome"'
                            }
                        }
                        stage ('test firefox shopizer panier') {
                            steps {
                                bat 'mvn clean test -DNavigateur="Firefox"'
                            }
                        }
                        stage ('test edge shopizer panier') {
                            steps {
                                bat 'mvn clean test -DNavigateur="Edge"'
                            }
                        }
                    }
                }
            }
        }
        stage('Neoload') {
            agent {label "built-in"}
            steps {
                build job: 'JobNeoload'
            }
        }
        stage('Production du jacoco-it.exec'){
            agent {
                label "built-in"
            }
            steps{
                sh '''
                sh apache-tomcat-8.5.73/bin/shutdown.sh
                mv ~/jacoco-it.exec sm-shop/target/jacoco-it.exec
                '''
            }
        }
        stage('Analyse sonar Shopizer 2.7.0'){
            agent {
                label "built-in"
            }
            steps{
                withSonarQubeEnv(installationName: 'SonarQube_6.7') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
        stage("Quality Gate") {
            steps {
                timeout(time: 2, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage('Upload du binaire testé'){
            agent {
                label "built-in"
            }
            steps{
                nexusPublisher nexusInstanceId: 'Nexus', nexusRepositoryId: 'ShopizerArtefact', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: 'sm-shop/target/ROOT.war']], mavenCoordinate: [artifactId: 'shopizer', groupId: 'com.shopizer', packaging: 'war', version: '2.7.0']]]
            }
        }
    }
}