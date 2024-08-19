function plannerModal(num) {
        $.ajax({
            url: "${path}/modal",
            data: {lNum: num},
            type: "post",
            dataType: 'json',
            success: function (data) {
                let img = '';
                img += 'url(\'/img/local/' + data.lNum + '/' + data.lNum + '-2.jpg\')';
                $(".modal-side > #modal-img").css("background-image", img);

                let content = '';
                content += '<h2 style="font-family:\'Montserrat\';font-weight: 900;margin-bottom:0;">' + data.kName + '</h2>' +
                    '<div style="font-family:\'Montserrat\';font-size: 1.2rem;padding-bottom:1rem;">' + data.kName + '</div>' +
                    '<input hidden name="planner-local" value="' + data.lNum + '">' +
                    '<button id="locationForModal" class="uk-button myro-color-button uk-button-large mt-3" style="float:right!important;" type="submit">리뷰작성</button>';
                $(".modal-body > #modal-content").html(content);
            }
        })
    }