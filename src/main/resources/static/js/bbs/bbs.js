async function toggleScrollableWindow(element, bbsID, bbsWriter, isFirstComment = true) {
  console.log("Clicked bbsID: " + bbsID);

  if(isFirstComment) {
      const commentInputForm =  document.getElementById("comment_input_form_" + bbsID);
      commentInputForm.style.display = commentInputForm.style.display === 'none' ? 'table-row' : 'none';
  }

  const commentsContainer = element.querySelector('.comments-container');

  // Check if comments container is empty
  const isEmpty = commentsContainer.innerHTML.trim() === '';

  if (isEmpty) {
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
        if(comment.comWriter === bbsWriter) {
            commentElement.innerHTML = '<table class="content-container"><tr></tr>' +
                          '<tr><td style="width: 75px;" rowspan="2"> <div class="userProfileImage"><img style="width:100%;" id="commentID_' + comment.comID + '" alt="Comment Profile Image"></div></td>'+ '<td>'
                            +' <div class="comment-writer writer">' + comment.comWriter + '</div>' + '<div class="comment-date timezone">' + formatDate(comment.writeDate) + '</div>' +'</td>' +'</tr>'
                         +'<tr><td><div class="comment-content">' + comment.comContent + '</div></td></tr></table>';
        } else {
            commentElement.innerHTML = '<table class="comments-container"><tr></tr>' +
              '<tr><td>' + '<div class="comment-date timezone">' + formatDate(comment.writeDate) + '</div>' +' <div class="comment-writer writer">' + comment.comWriter + '</div>' +'</td>'
                + '<td style="width: 75px;" rowspan="2"> <div class="userProfileImage"><img style="width:100%;" id="commentID_' + comment.comID + '" alt="Comment Profile Image"></div></td></tr>'
             +'<tr><td><div class="comment-content">' + comment.comContent + '</div></td></tr></table>';
        }
        commentsContainer.appendChild(commentElement);

        // Fetch user profile image
        const userProfileImage = document.getElementById('commentID_' + comment.comID);
        await getUserProfile(comment.comWriter, userProfileImage);
      }


    } catch (error) {
      console.log(error);
    }
  } else {
    // Empty the comments container
    commentsContainer.innerHTML = '';
  }
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
        await toggleScrollableWindow(element, bbsID, bbsWriter, false);
      } else {
        await toggleScrollableWindow(element, bbsID, bbsWriter);
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

