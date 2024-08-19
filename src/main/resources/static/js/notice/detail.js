const notice = {

   reset() {
      location.href = `/user/notice/listPage?num=1`;
   },
   previousOrNext($this) {
      const str = $this.hasClass("prev") ?"이전글이 존재하지 않습니다." : "다음글이 존재하지 않습니다.";

      $this.val() < 0 ? alert(str) : location.href = `/user/notice/detail/${$this.val()}`;
   },
   edit() {
      location.href = `/admin/notice/${$("#noticeNum").val()}`;
   },
   delete() {
      if (confirm("해당 글을 삭제하시겠습니까?")) {

         var Param = {
                noNum : $('#noticeNum').val()
         };

         $.post(`/admin/notice/delete`,Param)
               .then((data) => {
                if(data === 'success'){
                  alert("정상적으로 삭제되었습니다.");
                  location.href = `/user/notice/listPage?num=1`;
                }else{
                  alert("공지사항 삭제 중 오류가 발생했습니다.");
                }
               })
               .catch(() => {
                  alert("공지사항 삭제 중 오류가 발생했습니다.(catch)");
               });
      }
   }

}
$(function () {

   // notice - 최상단 이동
   $("#top").click(function () {
      window.scrollTo(0, 0);
   });

   // notice - 이전, 다음 게시글
   $(".prev, .next").click(function() {
      notice.previousOrNext($(this));
   });

   // notice - 게시글 1 page 이동
   $(".boardMainList").click(function () {
      notice.reset();
   });

   // notice - 게시글 수정
   $(".edit").click(function () {
      notice.edit();
   });

   // notice - 게시글 삭제
   $(".delete").click(function () {
      notice.delete();
   });

});

