function loginCheck(){
           const username = $('#username').val();
           const password = $('#password').val();
           let isValid = true;

           if (username === "") {
               $('#userIdError').text('원활한 진행을 위해 ID를 입력해주세요.');
               $('#username').focus();
               isValid= false;
           } else {
               $('#userIdError').text('');
           }

           if (password === "") {
               $('#pwdError').text('원활한 진행을 위해 비밀번호를 입력해주세요.');
               $('#password').focus();
               isValid= false;
           } else {
               $('#pwdError').text('');
           }

           if (isValid) {
                   document.form1.action = "/auth/login";
                   document.form1.submit();
               }
}
function pwdEnterKey(){
    const username = $('#username').val();
    const password = $('#password').val();

    if(window.event.keyCode === 13){
         let isValid = true;

                   if (username === "") {
                       $('#userIdError').text('원활한 진행을 위해 ID를 입력해주세요.');
                       $('#username').focus();
                       isValid= false;
                   } else {
                       $('#userIdError').text('');
                   }

                   if (password === "") {
                       $('#pwdError').text('원활한 진행을 위해 비밀번호를 입력해주세요.');
                       $('#password').focus();
                       isValid= false;
                   } else {
                       $('#pwdError').text('');
                   }

                   if (isValid) {
                           document.form1.action = "/auth/login";
                           document.form1.submit();
                       }
    }
}

jQuery(document).ready(function() {
    $('#loginCheck').on('click', loginCheck);
});


