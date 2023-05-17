function openModal() {
        // 모달 창 표시
        document.getElementById("myModal").style.display = "block";
        // 버튼을 감싸는 <div> 숨김
        document.getElementById("buttonWrapper").style.display = "none";
    }

    function closeModal() {
        // 모달 창 숨김
        document.getElementById("myModal").style.display = "none";
        // 버튼을 감싸는 <div> 표시
        document.getElementById("buttonWrapper").style.display = "block";
    }
