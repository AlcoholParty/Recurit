function deleteAlarm(f) {
    let idx = f.idx.value;
    let url = "/mypage/alarm/delete";
    let param = "idx=" + idx;
    sendRequest(url, param, deleteRes, "GET");
}

function deleteRes() {
    if ( xhr.readyState == 4 && xhr.status == 200 ) {
        var data = xhr.responseText;
        if(data == 'no'){
            alert("삭제 실패");
            location.reload();
        } else {
            alert("삭제 성공");
            location.reload();
        }
    }
}

function recruitAccept1(f) {
    let idx = f.idx.value;
    let recruitStudyIdx = f.recruitStudyIdx.value;
    let recruitMentorIdx = f.recruitMentorIdx.value;
    let recruitMenteeIdx = f.recruitMenteeIdx.value;
    let url = "/mypage/alarm/accept";
    let param = "idx=" + idx +
                "&recruitStudyIdx=" + recruitStudyIdx +
                "&recruitMentorIdx=" + recruitMentorIdx +
                "&recruitMenteeIdx=" + recruitMenteeIdx;
    sendRequest(url, param, acceptRes1, "GET");
}

function acceptRes1() {
    if ( xhr.readyState == 4 && xhr.status == 200 ) {
        var data = xhr.responseText;
        alert(data);
        if(data == "no"){
            alert("실패");
            return;
        } else {
            if(data == 'study') {
                alert("스터디원 구하기 수락 성공");
                location.reload();
            } else if(data == 'mentor') {
                alert("멘토 구하기 수락 성공");
                location.reload();
            } else if(data == 'mentee') {
                alert("멘티 구하기 수락 성공");
                location.reload();
            } else if(data == 'excess') {
                alert("인원이 다 구해져 받을 수 없습니다.");
                location.reload();
            }
        }
    }
}

function recruitRefuse(f) {
    let idx = f.idx.value;
    let recruitStudyIdx = f.recruitStudyIdx.value;
    let recruitMentorIdx = f.recruitMentorIdx.value;
    let recruitMenteeIdx = f.recruitMenteeIdx.value;
    let url = "/mypage/alarm/refuse"
    let param = "idx=" + idx;
    sendRequest(url, param, refuseRes, "GET");
}

function refuseRes() {
    if ( xhr.readyState == 4 && xhr.status == 200 ) {
        var data = xhr.responseText;
        if(data == 'no'){
            alert("거절 실패");
            location.reload();
        } else {
            alert("거절 성공");
            location.reload();
        }
    }
}