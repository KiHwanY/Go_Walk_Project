function getHtml(comment_num, review_num, mem_num, content) {

                var div = "<div class='commentbox2 mt-3'>" +
                    "<div class='d-flex row'>" +
                    "<input type='hidden' name='comment_num' value='" + comment_num + "'>" +
                    "<input type='hidden' name='mem_num' value='" + mem_num + "'>" +
                    "<input type='hidden' name='review_num' value='" + review_num + "'>" +
                    "<div class='col-sm-11'>" +
                    "<textarea id='editContent' name='editContent' oninput='textareaHeight()' onkeydown='resize(this)' onkeyup='resize(this)'>" + content + "</textarea>" +
                    "</div>" +
                    "<div class='col-sm-1 align-self-end'>" +
                    "<button class='btn btn-outline-success' type='button' id='commentEdit'>수정</button>" +
                    "<button class='btn btn-outline-success mt-3 cancel' type='button'>취소</button>" +
                    "</div>" +
                    "</div>" +
                    "</div>";
                return div;
            }

function textareaHeight() {
                        var textarea = document.getElementById("editContent");
                        textarea.style.height = "0px";
                        textarea.style.height = textarea.scrollHeight + "px";
                    }



$(function () {

            $(".editDiv").click(function () {
                var con = $(this).closest('.edit_view');
                var comment_num = $(this).data("comment_num");
                var review_num = $(this).data("review_num");
                var mem_num = $(this).data("mem_num");
                var content = $(this).data("content");

                if (!con.find('.commentbox2').length) {
                    con.append(getHtml(comment_num, review_num, mem_num, content));
                    textareaHeight();
                }
            });

            $(document).on("click", ".cancel", function () {
                $(this).closest('.commentbox2').remove();
            });


});
