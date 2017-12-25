<?php
        
		ini_set('display_errors', 1);
		
		$con = new mysqli("localhost", "root","root");
		
		if ($con->connect_error) {
			die("Connection failed: " . $con->connect_error);
		} 
		
		
        mysqli_select_db($con,"smartcare");
        
		
		$jsonObj = array();
		
		$surveyid = $_POST['surveyid'];
        
        if($surveyid!="null")
        {
			
			try
			{
				$qidsql = "SELECT * FROM surveyquestions as s WHERE s.surveyid = '".$surveyid."';";
				
				$qidResult = $con->query($qidsql);
				$qidcount= $qidResult->num_rows;
				
				if($qidcount>0)
				 {
						
					for($i=0;$i<$qidcount;$i++)
					{
						$rowqid = mysqli_fetch_array($qidResult,MYSQLI_ASSOC);
						
						$tempQid = $rowqid["qid"];
						$tempQuestion = $rowqid["questionname"];
						$tempQuestionType = $rowqid["questiontype"];
						
						
						if($tempQuestionType == "mcq")
						{
							$qidResponseResultsql = "SELECT qo.optionid,qo.optionname FROM surveyquestionsOptions as qo JOIN surveyquestions as q on q.qid=qo.qid WHERE q.qid = ".$tempQid;
									
									$qidResponseResult = $con->query($qidResponseResultsql);
									$qidResponseResultcount= $qidResponseResult->num_rows;
									
									if($qidResponseResultcount>0)
									{
										$qidRespObj = array();
										
										for($iiii=0;$iiii<$qidResponseResultcount;$iiii++)
										{
											$rowqidResponseResult = mysqli_fetch_array($qidResponseResult,MYSQLI_ASSOC);
											
											$qidResponseTempOptionId = $rowqidResponseResult["optionid"];
											$qidResponseTempOptionName = $rowqidResponseResult["optionname"];
											
											$row_arrayOp['optionid'] = $qidResponseTempOptionId;
											$row_arrayOp['optionname'] = $qidResponseTempOptionName;
											
											if($qidResponseTempOptionId == -1)
												$informationQuestionFlag = 1;

											array_push($qidRespObj,$row_arrayOp);
										
										}
										
										$row_arrayNo['qid'] = $tempQid;
										$row_arrayNo['options'] = $qidRespObj;
										
										$row_arrayNo['dataType'] = "mcq";
										$row_arrayNo['question'] = $tempQuestion;
									
										array_push($jsonObj,$row_arrayNo);
									}
						}
						else
						{
							$row_array['qid'] = $tempQid;
							$row_array['dataType'] = "text";
							$row_array['question'] = $tempQuestion;
									
							array_push($jsonObj,$row_array);
						}
						
					}
					
					
					$row_arrayFinal['success'] = true;
					$row_arrayFinal['questions'] = $jsonObj;
					
					echo json_encode($row_arrayFinal);
				 }
			}
			catch(Exception $e)
			{
				echo $e->getMessage();
			}
		}
		
?>