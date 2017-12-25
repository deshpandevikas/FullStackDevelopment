<?php
        
		ini_set('display_errors', 1);
		
		$con = new mysqli("localhost", "root","root");
		
		if ($con->connect_error) {
			die("Connection failed: " . $con->connect_error);
		} 
		
		
        mysqli_select_db($con,"smartcare");
        
		
		
		$sid = $_POST['sid'];
		$surveyname = $_POST['surveyname'];	
		$SurveyQuestions = $_POST['SurveyQuestions'];
		//$SurveyQuestions = json_decode($SurveyQuestionsTemp, true);
		

		
		$incrementTablesql = "SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA = 'smartcare' AND TABLE_NAME = 'Survey'";
		$incrementTableResult = $con->query($incrementTablesql);
		$surveyidRow = mysqli_fetch_array($incrementTableResult,MYSQLI_ASSOC);
		$surveyid = $surveyidRow['AUTO_INCREMENT'];
		
		
		
		$insertSurveySQL = "Insert into Survey(sid,surveyname) values(".$sid.",'".$surveyname."');" ;
		
		
		if ($con->query($insertSurveySQL) === TRUE) {
			
			
			for($i = 0; $i < count($SurveyQuestions); $i++){
				
				
				
				$eachQuestion = $SurveyQuestions[$i];
				
				
				
				$eachQuestionName = $eachQuestion["questionname"];
				
				if($eachQuestion["qType"]==1)
				{
					$insertQuestionSQL = "Insert into surveyquestions(sid,surveyid,questionname,questiontype) values(".$sid.",".$surveyid.",'".$eachQuestionName."','text');" ;
					
					$con->query($insertQuestionSQL);
					
				}
				else if($eachQuestion["qType"]==2)
				{
					$incrementTablesQidql = "SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA = 'smartcare' AND TABLE_NAME = 'surveyquestions'";
					$incrementTableQidResult = $con->query($incrementTablesQidql);
					$QidRow = mysqli_fetch_array($incrementTableQidResult,MYSQLI_ASSOC);
					$qid = $QidRow['AUTO_INCREMENT'];
					
					
					$insertQuestionSQL = "Insert into surveyquestions(sid,surveyid,questionname,questiontype) values(".$sid.",".$surveyid.",'".$eachQuestionName."','mcq');" ;
					$con->query($insertQuestionSQL);
					
					$options = $eachQuestion['options'];
					
					for($j=0; $j<count($options);$j++)
					{
						$tempj = $j+1;
						$insertOptions = "Insert into surveyquestionsOptions values (".$qid.",".$surveyid.",".$tempj.",'".$options[$j]['txt']."');";
						$con->query($insertOptions);
					}
					
				}
				else if($eachQuestion["qType"]==3)
				{
					$incrementTablesQidql = "SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA = 'smartcare' AND TABLE_NAME = 'surveyquestions'";
					$incrementTableQidResult = $con->query($incrementTablesQidql);
					$QidRow = mysqli_fetch_array($incrementTableQidResult,MYSQLI_ASSOC);
					$qid = $QidRow['AUTO_INCREMENT'];
					
					
					$insertQuestionSQL = "Insert into surveyquestions(sid,surveyid,questionname,questiontype) values(".$sid.",".$surveyid.",'".$eachQuestionName."','mcq');" ;
					$con->query($insertQuestionSQL);
					
					for($j=0; $j<=10;$j++)
					{
						$tempj = $j+1;
						$insertOptions = "Insert into surveyquestionsOptions(qid,surveyid,optionid,optionname) values (".$qid.",".$surveyid.",".$tempj.",'".$j."');";
						$con->query($insertOptions);
					}
					
				}
				
				
				
			}
			
		
			$row_arrayFinal['success'] = true;
			echo json_encode($row_arrayFinal);
		
		} 
		
		
		
		

          
        
 ?>