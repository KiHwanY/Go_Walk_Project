var main = {
	init : function() {
		var _this = this;
		$('#update').on('click',function() {
			_this.update();
            return;
		});


	},
	update : function() {

        	var mem_pass =$("#passwd").val();
        	if(mem_pass =="") {
        		alert("비밀번호를 필수 입력해주세요");
        		$("#passwd").focus();
        		return;
        	}

        	var mem_name =$("#name").val();
        	if(mem_name =="") {
        		alert("이름을 필수 입력해주세요");
        		$("#name").focus();
        		return;
        	}

            	var mem_nickname =$("#nickname").val();
            	if(mem_nickname =="") {
            		alert("닉네임을 필수 입력해주세요");
            		$("#nickname").focus();
            		return;
            	}

        	var mem_zipcode =$("#zipcode").val();
        	if(mem_zipcode =="") {
        		alert("우편번호 찾기를 누르세요");
        		$("#zipcode").focus();
        		return;
        	}
        var mem_numStr = $('#mem_num').val();
        var mem_zipcodeStr = $('#zipcode').val(); // 전화번호 문자열
        var memBirthStr = $('#birth').val(); // 생년월일 문자열

        var regex = /^[0-9]+$/;

         // memPhone 변환
            if (mem_numStr && regex.test(mem_numStr)) {
                var memNumCode = parseInt(mem_numStr, 10);
                console.log("변환된 IDX:", memNumCode);
            } else {
                console.error("유효하지 않은 IDX:", mem_numStr);
            }

            if (mem_zipcodeStr && regex.test(mem_zipcodeStr)) {
                var memZipCode = parseInt(mem_zipcodeStr, 10);
                console.log("변환된 우편변호:", memZipCode);
            } else {
                console.error("유효하지 않은 전화번호:", mem_zipcodeStr);
            }

            // memBirth 변환
            if (memBirthStr && regex.test(memBirthStr)) {
                var memBirth = parseInt(memBirthStr, 10);
                console.log("변환된 생년월일:", memBirth);
            } else {
                console.error("유효하지 않은 생년월일:", memBirthStr);
            }

		var param = {
		        memNum : memNumCode,
				memPass: mem_pass,
				memName: mem_name,
				memNickName : mem_nickname,
				memZipCode : memZipCode,
				memAddress1 : $('#address1').val(),
				memAddress2 : $('#address2').val(),
				memPhone : $('#tel').val(),
				memBirth : memBirth

		};

		console.log('data  = ' , param);

		$.ajax({
			type: 'POST',
			url: '/user/updateMember',
			data: param,
			success : function(data){
			    if(data = "success"){
			       alert('회원 정보를 수정하였습니다.');
			    }else{
			        alert('서비스 로직 확인하세요.');
			    }

			},
			error : function(){
			    alert('에러 발생했습니다. 서비스 로직 확인하세요.');
			}
		});
	}
};
function daumZipCode() {
	new daum.Postcode({
        oncomplete: function(data) {
            var addr = '';
            var extraAddr = '';
            if (data.userSelectedType === 'R') {
                addr = data.roadAddress;
            } else {
                addr = data.jibunAddress;
            }
            if(data.userSelectedType === 'R'){
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
                document.getElementById("address1").value = extraAddr;
            } else {
                document.getElementById("address2").value = '';
            }
            document.getElementById('zipcode').value = data.zonecode;
            document.getElementById("address1").value = addr;
            document.getElementById("address2").focus();
        }
    }).open();
}

              function checkPass() {
                var mem_pass = document.getElementById('passwd').value;

                var pattern = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\d~!@#$%^&*()+|=]{8,16}$/;

                if (pattern.test(mem_pass)) {
                  document.getElementById('pass-error').textContent = '';
                  document.getElementById('pass-ok').textContent = '비밀번호 사용가능!';
                } else {
                  document.getElementById('pass-error').textContent = '숫자,문자,특수문자를 사용하여 최소 8자이상 16자이하';
                  document.getElementById('pass-ok').textContent = '';
                }
              }

              function checkPassMatch() {
                var password = document.getElementById('passwd').value;
                var confirmPassword = document.getElementById('passwd_ck').value;

                if (password !== confirmPassword) {
                  document.getElementById('pass-match-error').textContent = '비밀번호가 일치하지 않습니다.';
                  document.getElementById('pass-match').textContent = '';
                } else {
                  document.getElementById('pass-match-error').textContent = '';
                  document.getElementById('pass-match').textContent = '비밀번호가 일치합니다.';
                }
              }
              function checkPhoneMatch() {
                 var checkPhone = document.getElementById('tel').value;
                 var pattern = /^(010\d{4}\d{4}|010-\d{4}-\d{4})$/;

                 if (pattern.test(checkPhone)) {
                  document.getElementById('phone-match-error').textContent = '';
                  document.getElementById('phone-match').textContent = '전화번호 등록 완료!!';
                 } else {
                  document.getElementById('phone-match-error').textContent = '전화번호는 숫자만 가능합니다.';
                  document.getElementById('phone-match').textContent = '';
                 }
              }
              function checkBirthMatch() {
                 var checkBirth = document.getElementById('birth').value;
                 var pattern = /^\d{2}[01]\d[0-3]\d$/;

                 if (pattern.test(checkBirth)) {
                  document.getElementById('birth-match-error').textContent = '';
                  document.getElementById('birth-match').textContent = '생년월일 등록 완료!!';
                 } else {
                  document.getElementById('birth-match-error').textContent = '생년월일은 숫자만 가능합니다.';
                  document.getElementById('birth-match').textContent = '';
                 }
              }

 $('#back').on('click',function() {
           history.back();
    return;
 });
jQuery(document).ready(function(){
    main.init();


});
