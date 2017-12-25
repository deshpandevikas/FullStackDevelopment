<?php
        
		ini_set('display_errors', 1);
		
		$con = new mysqli("localhost", "root","root");
		
		if ($con->connect_error) {
			die("Connection failed: " . $con->connect_error);
		} 
		
		
        mysqli_select_db($con,"smartcare");
        
		
		$jsonObj = array();
		
		$name1 = $_POST['name1'];
		
		$sid = $_POST['sid'];
        
        if($name1!="null")
        {
			
			try
			{
				
			
			if($name1!=null)
			{
				
				
				$qidsql = "SELECT * FROM questions WHERE sid = '".$sid."';";
				
				$qidResult = $con->query($qidsql);
				$qidcount= $qidResult->num_rows;
				
				 if($qidcount>0)
				 {
						
					for($i=0;$i<$qidcount;$i++)
					{
						$rowqid = mysqli_fetch_array($qidResult,MYSQLI_ASSOC);
						$tempQid = $rowqid["qid"];
						
						$timepostedsql = "SELECT * FROM questionsTimesPosted WHERE qid = ".$tempQid.";";
						
						$timePostedResult = $con->query($timepostedsql);
						$timepostedcount= $timePostedResult->num_rows;
						
						if($timepostedcount>0)
						{
							
							for($ii=0;$ii<$timepostedcount;$ii++)
							{
								$rowtimeposted = mysqli_fetch_array($timePostedResult,MYSQLI_ASSOC);
								
								$tempTimePostedResult = $rowtimeposted["timeId"];
								$tempTimePostedResultTimeStamp = $rowtimeposted["dtime"];
								
								$timeresponsesql = "SELECT qr.qid,qr.timeId,qr.optionid,qo.optionname,q.question FROM questions as q JOIN questionsTimesPosted as tp on q.qid = tp.qid JOIN questionsResponse as qr on  qr.timeId = tp.timeId JOIN questionsOptions as qo on qo.optionid = qr.optionid and qo.qid = qr.qid WHERE qr.uemail = '".$name1."' and qr.timeId = ".$tempTimePostedResult." and qr.qid = ".$tempQid;
								
								$timeresponseresult = $con->query($timeresponsesql);
								$timeresponsecount= $timeresponseresult->num_rows;
								
								if($timeresponsecount>0)
								{
									for($iii=0;$iii<$timeresponsecount;$iii++)
									{
										$rowtimeresponse = mysqli_fetch_array($timeresponseresult,MYSQLI_ASSOC);
										
										$timeIdTemp = $rowtimeresponse["timeId"];
										$optionIdTemp = $rowtimeresponse["optionid"];
										$optionNameTemp = $rowtimeresponse["optionname"];
										$questionActualTemp = $rowtimeresponse["question"];
										
										$row_array['qid'] = $tempQid;
										$row_array['timeId'] = $timeIdTemp;
										$row_array['optionId'] = $optionIdTemp;
										$row_array['optionName'] = $optionNameTemp;
										$row_array['dataType'] = "question";
										$row_array['response'] = "yes";
										$row_array['question'] = $questionActualTemp;
										$row_array['dtime'] = $tempTimePostedResultTimeStamp;
										
										array_push($jsonObj,$row_array);
										
									}
								}
								else
								{
									$qidResponseResultsql = "SELECT q.question,qo.qid,qo.optionid,qo.optionname FROM questionsOptions as qo JOIN questions as q on q.qid=qo.qid WHERE q.qid = ".$tempQid;
									
									$qidResponseResult = $con->query($qidResponseResultsql);
									$qidResponseResultcount= $qidResponseResult->num_rows;
									
									$questionActualTemp2 = "temp";

									
									$informationQuestionFlag = 0;
									
									if($qidResponseResultcount>0)
									{
										$qidRespObj = array();
										
										for($iiii=0;$iiii<$qidResponseResultcount;$iiii++)
										{
											$rowqidResponseResult = mysqli_fetch_array($qidResponseResult,MYSQLI_ASSOC);
											
											$qidResponseTempQid = $rowqidResponseResult["qid"];
											$qidResponseTempOptionId = $rowqidResponseResult["optionid"];
											$qidResponseTempOptionName = $rowqidResponseResult["optionname"];
											$questionActualTemp2 = $rowqidResponseResult["question"];
											
											
											$row_arrayOp['optionid'] = $qidResponseTempOptionId;
											$row_arrayOp['optionname'] = $qidResponseTempOptionName;
											
											if($qidResponseTempOptionId == -1)
												$informationQuestionFlag = 1;

											array_push($qidRespObj,$row_arrayOp);
										
										}
										
										$row_arrayNo['qid'] = $tempQid;
										$row_arrayNo['timeId'] = $tempTimePostedResult;
										$row_arrayNo['options'] = $qidRespObj;
										
										if($informationQuestionFlag==0)
											$row_arrayNo['response'] = "no";
										else
											$row_arrayNo['response'] = "NA";
										
										$row_arrayNo['dataType'] = "question";
										$row_arrayNo['question'] = $questionActualTemp2;
										$row_arrayNo['dtime'] = $tempTimePostedResultTimeStamp;
										
										array_push($jsonObj,$row_arrayNo);
												
									}
									
									
								}
								
								
							}
							
						}
						

					}
					
					
				 
				 }
				 
					$surveysql = "SELECT * FROM Survey as s WHERE s.sid = '".$sid."';";
				 
				 
					$surveyResult = $con->query($surveysql);
					$surveycount= $surveyResult->num_rows;
					
					 if($surveycount>0)
					 {
							
						for($i=0;$i<$surveycount;$i++)
						{
							
							$rowsurvey = mysqli_fetch_array($surveyResult,MYSQLI_ASSOC);
							
							$surveyid = $rowsurvey["surveyid"];
							
							$surveyResponsesql = "SELECT * FROM surveyquestionsResponse as s WHERE s.surveyid = '".$surveyid."' and s.uemail = '".$name1."';";
							
							
							$surveyResponseResult = $con->query($surveyResponsesql);
							$surveyResponsecount= $surveyResponseResult->num_rows;
							
							if($surveyResponsecount>0)
							{
								$row_arraySurvey['response'] = "yes";
							}
							else
							{
								$row_arraySurvey['response'] = "no";
							}
							
							
							$row_arraySurvey['dataType'] = "survey";
							$row_arraySurvey['surveyid'] = $rowsurvey["surveyid"];
							$row_arraySurvey['surveyname'] = $rowsurvey["surveyname"];
							$row_arraySurvey['dtime'] = $rowsurvey["dtime"];
										
							array_push($jsonObj,$row_arraySurvey);	
							
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