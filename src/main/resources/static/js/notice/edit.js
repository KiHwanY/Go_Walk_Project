const notice = {

   reset() {
       history.back();
   },
   edit() {
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
                noNum : $('#noNum').val(),
                memNum : $('#memNum').val(),
                title : $title.val(),
                content : $content.val()
           };

           $.post(`/admin/notice/edit`, Param)
           .then((data) => {
              if(data === 'success'){
                  alert("정상적으로 수정되었습니다.");
                  location.href=`/user/notice/detail/${$('#noNum').val()}`;
              }else{
                   alert("공지사항 수정 중 오류가 발생했습니다.");
              }
           })
           .catch(() => {
              alert("공지사항 수정 중 오류가 발생했습니다.(catch)");
           });
   }
}
$(function () {

   // notice - 게시글 수정
   $("#edit").click(function () {
      notice.edit();
   });

    $("#reset").click(function () {
         notice.reset();
      });
});
