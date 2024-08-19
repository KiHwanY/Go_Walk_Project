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
            title : $title.val(),
            content : $content.val()
		};

		$.ajax({
			type: 'POST',
			url: '/admin/notice/insert',
			data: param,
			success : function(data){
			    if (data == "success"){
			       alert('공지사항 등록 완료! 게시판 목록으로 이동합니다.');
                   location.href='/user/notice/listPage?num=1';
                   return;
                 }else{
                     alert('공지사항 등록 중 에러가 발생했습니다.');
                 }
			},
			error : function(){
			    alert('공지사항 등록 중 에러가 발생했습니다.');
			}
		});
	}
};


jQuery(document).ready(function() {

    main.init();

    $("#write").click(function () {
        location.href = `/admin/notice/write`;
    });
    $("#reset").click(function () {
        location.href = `/user/notice/listPage?num=1`;
    });

});