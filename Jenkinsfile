pipeline {
    agent any
   
stage('testing pipeline'){
          cmd_exec('echo "Buils starting..."')
      cmd_exec('newman run "C:\\Users\\redshift\\Desktop\\TimeSheet.postman_collection" -e "C:\\Users\\redshift\\Desktop\\test.postman_environment.json"')
}

def cmd_exec(command) {
    return bat(returnStdout: true, script: "${command}").trim()
}
}