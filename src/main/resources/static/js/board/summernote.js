jQuery(document).ready(function(){

            $('.summernote').summernote({
                height: 500,
                lang: "ko-KR",
                minHeight: 500,
                maxHeight: 500,
                focus: true,
                toolbar: [
                    ['fontname', ['fontname']],
                    ['style', ['bold', 'italic', 'underline', 'strikethrough', 'clear']],
                    ['color', ['forecolor', 'color']],
                    ['table', ['table']],
                    ['para', ['ul', 'ol', 'paragraph']],
                    ['insert', ['picture', 'link', 'video']],
                    ['view', ['fullscreen', 'help']]
                ],
                fontNames: ['Arial', 'Arial Black', 'Comic Sans MS', 'Courier New', '맑은 고딕', '궁서', '굴림체', '굴림', '돋음체', '바탕체'],
                callbacks: {
                    onImageUpload: function (files, editor, welEditable) {
                        // 파일 업로드(다중업로드를 위해 반복문 사용)
                        for (var i = files.length - 1; i >= 0; i--) {
                            uploadSummernoteImageFile(files[i], this);
                        }
                    }
                }
            });

            function uploadSummernoteImageFile(file, el) {
                data = new FormData();
                data.append("file", file);
                $.ajax({
                    data: data,
                    type: "POST",
                    url: "/user/uploadSummernoteImageFile",
                    contentType: false,
                    enctype: 'multipart/form-data',
                    processData: false,
                    success: function (data) {
                        $(el).summernote('editor.insertImage', data.url);
                    }
                });
            }
});
