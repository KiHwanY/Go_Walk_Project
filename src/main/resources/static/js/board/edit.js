const review = {

   reset() {
       history.back();
   },
   edit() {
            $lNum = $('#editLocaltionSelect');
            if (lNum == "지역") {
                alert("지역을 선택해주세요");
                $("#editLocaltionSelect").focus();
                return;
            }

            var $title = $("#title");
            if (!$title.val()) {
                alert("제목을 필수 입력해주세요");
                $title.focus();
                return;
            }

            var $content = $('textarea[name="content"]');
            if (!$content.val()) {
                alert("내용을 필수 입력해주세요");
                $content.focus();
                return;
            }

           var Param = {
                rNum : $('#rNum').val(),
                memNum : $('#memNum').val(),
                lNum : $lNum.val(),
                title : $title.val(),
                content : $content.val()
           };

           $.post(`/user/review/edit`, Param)
           .then((data) => {
              if(data === 'success'){
                  alert("정상적으로 수정되었습니다.");
                  location.href=`/user/review/detail/${$('#rNum').val()}`;
              }else{
                   alert("review 수정 중 오류가 발생했습니다.");
              }
           })
           .catch(() => {
              alert("review 수정 중 오류가 발생했습니다.(catch)");
           });
   }
}
$(function () {

   // review - 게시글 수정
   $("#edit").click(function () {
      review.edit();
   });

    $("#reset").click(function () {
         review.reset();
      });
});
