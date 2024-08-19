var locationValue = "";

var main = {
	init : function() {
		var _this = this;
		$('#insert').on('click',function() {
			_this.save();
		});

		let lastPageNum = $('#lastPageNum').val();
		let pageBody = [];

		for (let i = 1; i <= lastPageNum; i++) {
		    pageBody.push(`
                <a class="ms-3 text-muted" onclick="optionChange('num',${i})">${i}</a>
            `);
		}

        $('#pageList').append(pageBody.join(''));

	},

	save : function() {
        var lNum = $('select[name="lNum"]').val();
        console.log('lNum = ', lNum);
        if (lNum == "지역") {
            alert("지역을 선택해주세요");
            $("#insertLocaltionSelect").focus();
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

		var param = {
            memNum : $('#memNum').val(),
            lNum,
            title : $title.val(),
            content : $content.val()
		};

		$.ajax({
			type: 'POST',
			url: '/user/review/insert',
			data: param,
			success : function(data){
			    if (data == "success"){
			       alert('리뷰 글 등록 완료! 게시판 목록으로 이동합니다.');
                   location.href='/user/review/listPage?num=1';
                   return;
                 }

                alert('서비스 로직 확인!');

			},
			error : function(){
			    alert('에러 발생');
			}
		});
	}
};


jQuery(document).ready(function() {

    main.init();

    $("#write").click(function () {
        location.href = `/user/review/write`;
    });

    $("#reset").click(function () {
          location.href = `/user/review/listPage?num=1`;
       });


});