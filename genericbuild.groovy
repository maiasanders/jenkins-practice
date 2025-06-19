def call(Map config[:]) {
    node {
    stage('SCM') {
        echo 'Gathering code from version control'
        git branch: '${branch}', url: 'https://github.com/FeynmanFan/JenkinsGroovy.git'
    }
    stage('build') {
        try {
            echo 'building....'
            withDotNet(sdk: 'sdk6'){
                sh 'dotnet build ConsoleApp1'
                echo 'building new features'
                releaseNotes(changes: "true")
            }
        } catch(ex) {
            echo 'oops'
            echo ex.toString()
            currentBuild.result = 'FAILURE'
        }
        finally {
            echo 'cleaning up'
        }
    }
    stage('test') {
        echo 'testing....'
    }
    stage('deploy') {
        echo 'deploying...'
    }
}

}
