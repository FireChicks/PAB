async function toggleScrollableWindow(element, bbsID, bbsWriter) {
  console.log("Clicked bbsID: " + bbsID);
  var delBtnAdd =false;
  const commentsContainer = element.querySelector('.comments-container');
    const isEmpty = commentsContainer.innerHTML.trim() === '';
    if(!isEmpty){
      return;
    }

      const commentInputForm =  document.getElementById("comment_input_form_" + bbsID);
      commentInputForm.style.display = 'table-row';



    try {
      const response = await axios.get('/comment/read', {
        params: {
          bbsID: bbsID
        }
      });

      const comments = response.data;
      if (!comments || comments.length === 0) {
        // No comments returned, exit the function
        return;
      }

      console.log("Comments: ", comments);

      for (const comment of comments) {
        const commentElement = document.createElement('div');
        commentElement.classList.add('comment');
        /*글 작성자와 다른사람의 댓글인지 구분*/
        if(comment.comWriter === bbsWriter) {
            if(userID != null && comment.comWriter === userID) { //삭제버튼 표시 유무 구분
                commentElement.innerHTML = '<table class="content-container"><tr></tr>' +
                                          '<tr><td style="width: 75px;" rowspan="2"> <div class="userProfileImage"><img style="width:100%;" id="commentID_' + comment.comID + '" alt="Comment Profile Image"></div></td>'+ '<td>'
                                            +' <div class="comment-writer writer">' + comment.comWriter + '</div>' + ' <div class="comment-date timezone">' + formatDate(comment.writeDate) + '<button id="comDel'+ comment.comID +'" type="button" class="btn-close" aria-label="Close"></button> </div>' +'</td>' +'</tr>'
                                         +'<tr><td><div class="comment-content">' + comment.comContent + '</div></td></tr></table>';
                delBtnAdd = true;

            } else {
                commentElement.innerHTML = '<table class="content-container"><tr></tr>' +
                                          '<tr><td style="width: 75px;" rowspan="2"> <div class="userProfileImage"><img style="width:100%;" id="commentID_' + comment.comID + '" alt="Comment Profile Image"></div></td>'+ '<td>'
                                            +' <div class="comment-writer writer">' + comment.comWriter + '</div>' + ' <div class="comment-date timezone">' + formatDate(comment.writeDate) + '</div>' +'</td>' +'</tr>'
                                         +'<tr><td><div class="comment-content">' + comment.comContent + '</div></td></tr></table>';
            }

        } else {
            if(userID != null && comment.comWriter === userID) { //삭제버튼 표시 유무 구분
                commentElement.innerHTML = '<table class="comments-container"><tr></tr>' +
                              '<tr><td>' + '<div class="comment-date timezone"><button id="comDel'+ comment.comID +'" type="button" class="btn-close" aria-label="Close"></button>' + formatDate(comment.writeDate) + '</div>' +' <div class="comment-writer writer">' + comment.comWriter + '</div>' +'</td>'
                                + '<td style="width: 75px;" rowspan="2"> <div class="userProfileImage"><img style="width:100%;" id="commentID_' + comment.comID + '" alt="Comment Profile Image"></div></td></tr>'
                             +'<tr><td><div class="comment-content">' + comment.comContent + '</div></td></tr></table>';
                delBtnAdd = true;
            } else {
                commentElement.innerHTML = '<table class="comments-container"><tr></tr>' +
                              '<tr><td>' + '<div class="comment-date timezone">' + formatDate(comment.writeDate) + '</div>' +' <div class="comment-writer writer">' + comment.comWriter + '</div>' +'</td>'
                                + '<td style="width: 75px;" rowspan="2"> <div class="userProfileImage"><img style="width:100%;" id="commentID_' + comment.comID + '" alt="Comment Profile Image"></div></td></tr>'
                             +'<tr><td><div class="comment-content">' + comment.comContent + '</div></td></tr></table>';
            }

        }

        if(comment.partID != null) {
            if(!(comment.comWriter === bbsWriter)) {
                commentElement.innerHTML += makePartViewHtml(comment.partCategory, comment.partID, comment.partImgLink);
            } else {
                commentElement.innerHTML += "<div style='float:left;'>" + makePartViewHtml(comment.partCategory, comment.partID, comment.partImgLink) + "</div>";
            }
        }

        commentsContainer.appendChild(commentElement);

         if(delBtnAdd){
                    const button = document.getElementById("comDel" + comment.comID);

                    // 클릭 이벤트 리스너를 추가합니다.
                    button.addEventListener("click", function() {
                    deleteComment(element, bbsID, bbsWriter, comment.comID);
                });
         }

        // Fetch user profile image
        const userProfileImage = document.getElementById('commentID_' + comment.comID);
        await getUserProfile(comment.comWriter, userProfileImage);
      }


    } catch (error) {
      console.log(error);
    }

  var scrollableContainer = element.querySelector('.scrollable-container');
    scrollableContainer.style.overflowY = 'scroll';
    element.classList.toggle('expanded');
     const contentContainer = element.querySelector('.scrollable-container');
            contentContainer.scrollTop = contentContainer.scrollHeight;

}

function restScorollableWindow(element, bbsID) {
    const commentsContainer = element.querySelector('.comments-container'); //댓글부분 안보이게
    commentsContainer.innerHTML = '';

    const commentInputForm =  document.getElementById("comment_input_form_" + bbsID); //입력 폼 안 보이게
    commentInputForm.style.display = 'none';

    var scrollableContainer = element.querySelector('.scrollable-container');
        scrollableContainer.style.overflowY = 'scroll';
        element.classList.toggle('expanded');
         const contentContainer = element.querySelector('.scrollable-container');
                contentContainer.scrollTop = contentContainer.scrollHeight;
}

async function moveScrollToFloor(element) {
  var scrollableContainer = element.querySelector('.scrollable-container');
  scrollableContainer.style.overflowY = 'scroll';
  element.classList.toggle('expanded');
  await new Promise(resolve => setTimeout(resolve, 0)); // Wait for rendering
  const contentContainer = element.querySelector('.scrollable-container');
  contentContainer.scrollTop = contentContainer.scrollHeight;
}

async function handleSubmit(event, element, bbsID, bbsWriter) {
  event.preventDefault(); // Prevent the default form submission behavior

  const form = event.target;
  const formData = new FormData(form);
  formData.append('bbsID', bbsID); // Add bbsID to the FormData

  try {
    const response = await axios.post('/comment/writeComment', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });

    const isSuccess = response.data === true; // Check if the response is 'true'

    if (isSuccess) {
      const commentsContainer = element.querySelector('.comments-container');
      const isEmpty = commentsContainer.innerHTML.trim() === '';
      if(isEmpty) {
        await toggleScrollableWindow(element, bbsID, bbsWriter);
      } else {
        restScorollableWindow(element, bbsID);
        await toggleScrollableWindow(element, bbsID, bbsWriter);
      }

      // Reset the textarea value
      const textarea = document.getElementById('comContent_' + bbsID);
      textarea.value = '';
      textarea.focus();
    } else {
      // If the response is 'false', display an alert message
      alert('로그인 해주세요.'); // Display the alert message
    }
  } catch (error) {
    console.log(error);
  }
}

async function deleteComment(element, bbsID, bbsWriter, comID) {
  restScorollableWindow(element, bbsID);

  try {
    await axios.get('/comment/deleteCom', {
      params: {
        comID: comID
      }
    });

    await toggleScrollableWindow(element, bbsID, bbsWriter);
    restScorollableWindow(element, bbsID);
    await toggleScrollableWindow(element, bbsID, bbsWriter);
  } catch (error) {
    console.log(error);
  }
}




function getUserProfile(userID, imgaeElement) {
    axios.get('/user/getUserProfile', {
        params: {
            userID: userID
        }
    })
    .then(function(response) {
        var encodeString = response.data;
        imgaeElement.src = 'data:image/jpeg;base64,' + encodeString;
    })
    .catch(function(error) {
        console.log(error);
    });
}

function formatDate(dateString) {
  var date = new Date(dateString);
  var year = date.getFullYear();
  var month = addZeroPadding(date.getMonth() + 1);
  var day = addZeroPadding(date.getDate());
  var ampm = getAMPM(date.getHours());
  var hours = addZeroPadding(getHours12Format(date.getHours()));
  var minutes = addZeroPadding(date.getMinutes());
  var seconds = addZeroPadding(date.getSeconds());

  return year + '.' + month + '.' + day + ' ' + ampm + ' ' + hours + ':' + minutes + ':' + seconds;
}

function addZeroPadding(value) {
  return value.toString().padStart(2, '0');
}

function getAMPM(hours) {
  return hours >= 12 ? 'PM' : 'AM';
}

function getHours12Format(hours) {
  return hours % 12 || 12;
}

function makePartViewHtml(partsCategory, partID, partImgSrc) {
    var html = "<br>";
    var categoryURL;
    if (partsCategory === "cpu") {
        categoryURL = "cpuView?cpuID=" + partID;
    } else if (partsCategory === "mb") {
        categoryURL = "mbView?mbID=" + partID;
    } else if (partsCategory === "ram") {
        categoryURL = "ramView?ramID=" + partID;
    } else if (partsCategory === "sto") {
        categoryURL = "stoView?stoID=" + partID;
    } else if (partsCategory === "pow") {
        categoryURL = "powView?powID=" + partID;
    } else if (partsCategory === "gpu") {
        categoryURL = "gpuView?gpuID=" + partID;
    }

    html += "<a href='" + categoryURL + "'><img style='width:150px; height: 150px;' src='" + partImgSrc + "'></a>";

    return html;

}

function deleteBbs(page, pageSize, searchText, searchCategory, bbsID) {
    isDelete = confirm("정말로 글을 삭제하시겠습니까?");
    if(isDelete) {
        if(searchText == null) {
            searchText = "";
        }
        if(searchCategory == null) {
                    searchCategory = "bbsTitle";
        }
        location.href="/bbs/deleteBbs?page=" + page
                                + "&pageSize=" + pageSize
                                + "&searchText=" + searchText
                                + "&searchCategory=" + searchCategory
                                + "&bbsID=" + bbsID;
    } else {
        return;
    }
}
