const review = {

   reset() {
      location.href = `/user/review/listPage?num=1`;
   },
   previousOrNext($this) {
      const str = $this.hasClass("prev") ?"이전글이 존재하지 않습니다." : "다음글이 존재하지 않습니다.";

      $this.val() < 0 ? alert(str) : location.href = `/user/review/detail/${$this.val()}`;
   },
   edit() {
      location.href = `/user/review/editPage/${$("#reviewNum").val()}`;
   },
   delete() {
      if (confirm("해당 글을 삭제하시겠습니까?")) {

         var Param = {
                rNum : $('#reviewNum').val()
         };

         $.post(`/user/review/delete`,Param)
               .then((data) => {
                if(data === 'success'){
                  alert("정상적으로 삭제되었습니다.");
                  location.href = `/user/review/listPage?num=1`;
                }else{
                  alert("review 삭제 중 오류가 발생했습니다.");
                }
               })
               .catch(() => {
                  alert("review 삭제 중 오류가 발생했습니다.(catch)");
               });
      }
   }

}

// review - 댓글
const comment = {

   search () {
      var Param = {
                   rNum : $('#reviewNum').val(),
              };

      $.get(`/user/comment/commentsList`,Param)
      .then((result) => {
         $("#commentList").html(result);
      })
      .catch(() => {
         alert("review 조회 중 오류가 발생했습니다.");
      });

   },
   insert () {
      let content = $("#content").val();

      if(!content){
          alert("댓글을 입력해주세요.");
          $("#content").focus();
          return;
      }
      var Param = {
             rNum : $('#reviewNum').val(),
             memNum : $('#memNum').val(),
             content : content
        };

      $.post(`/user/comment/insert`, Param)
      .then((data) => {
       if(data === 'success'){
         alert("정상적으로 등록되었습니다.");
         $('#content').val('');
         comment.search();
        }else{
         alert("댓글 등록 중 오류가 발생했습니다.");
        }
      })
      .catch(() => {
         alert("댓글 등록 중 오류가 발생했습니다.(catch)");
      });

   },
   delete (cNum) {

      var Param = {
                   cNum : cNum
              };

      $.post(`/user/comment/delete`, Param)
      .then((data) => {
      if(data === 'success'){
         alert("정상적으로 삭제되었습니다.");
         comment.search();
         }else{
            alert("댓글 삭제 중 오류가 발생했습니다.");
         }
      })
      .catch(() => {
         alert("댓글 삭제 중 오류가 발생했습니다.(catch)");
      });
   }
};

$(function () {
   comment.search();

   // review - 최상단 이동
   $("#top").click(function () {
      window.scrollTo(0, 0);
   });

   // review - 이전, 다음 게시글
   $(".prev, .next").click(function() {
      review.previousOrNext($(this));
   });

   // review - 게시글 1 page 이동
   $(".boardMainList").click(function () {
      review.reset();
   });

   // review - 게시글 수정
   $(".edit").click(function () {
      review.edit();
   });

   // review - 게시글 삭제
   $(".delete").click(function () {
      review.delete();
   });

   // comments - 댓글 추가
   $("#commentInsert").click(function () {
      comment.insert();
   });

});

function resize(obj) {
   obj.style.height = '1px';
   obj.style.height = (22 + obj.scrollHeight) + 'px';
}