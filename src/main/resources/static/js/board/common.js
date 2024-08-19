

function localList(division) {
   $.get("/user/local")
   .then((response) => {

        let localArray = [];
        let selectLocalValue = $("#lNum").val();

        localArray.push(`<option>지역</option>`);

        for (const {lnum, kname} of response) {
            if (selectLocalValue == lnum) {
                localArray.push(`<option selected value="${lnum}">${kname}</option>`);
                continue;
            }

            localArray.push(`<option value="${lnum}">${kname}</option>`);
        }

        const elementId = division === 'insert' ? "#insertLocaltionSelect" : "#editLocaltionSelect"
        $(elementId).html(localArray.join(''));

   })
   .catch(() => {

   });
}

