var checkedBrandCategory;
var checkedSocketCategory;
let cpuInfos = [];
const pageSize = 10;

// 서버로부터 CPU 정보를 가Fuwbdiw져와 테이블에 추가하는 함수
function fetchAndAddCPUsToTable(page = 1, filter = '') {
  axios.get(`/cpu`)
    .then(function (response) {
      cpuInfos = response.data;
      paginate(1,'');
    })
    .catch(function (error) {
      console.error(error);
    });
}

function inputToTableBody(row, tableBody, info){
        if(info.cpuName.length <= 50) {
            row.insertCell(0).innerHTML = `<a href="${info.amazon_Link}">${info.cpuName}</a>`;
        } else{
            row.insertCell(0).innerHTML = `<a href="${info.amazon_Link}">${info.cpuName.substring(0, 50)+'...'}</a>`;
        }
        row.insertCell(1).innerHTML = info.brand;
        row.insertCell(2).innerHTML = info.cpuModel;
        row.insertCell(3).innerHTML = info.cpuSpeed;
        row.insertCell(4).innerHTML = info.cpu_socket;
        row.insertCell(5).innerHTML = '미구현';
        row.insertCell(6).innerHTML = `<a href="${info.amazon_Link}"><img src="${info.amazon_img_link}" width="150" height="150"></a>`;
        row.insertCell(7).innerHTML = '<button type="button" class="btn btn-primary">추가</button>';
}


function paginate(page,inputBrand) {
  const totalItems = cpuInfos.length;
  const totalPages = Math.ceil(totalItems / pageSize);
  const startIndex = (page - 1) * pageSize;
  const endIndex = Math.min(startIndex + pageSize, totalItems);

  const tableBody = document.getElementById('CPUInfoTableBody');
  tableBody.innerHTML = ''; // 테이블 초기화
  for (let i = 0; i < totalItems; i++) {
    // 테이블에 데이터 추가
    const cpuInfo = cpuInfos[i];
    const row = tableBody.insertRow(-1);
    inputToTableBody(row,tableBody,cpuInfo);
  }

  // 남은 데이터들은 hide 처리
  const tableRows = $('#CPUInfoTableBody tr');
  tableRows.hide(); // 모든 데이터 숨기기
  tableRows.slice(startIndex, endIndex).show(); // 페이지에 해당하는 데이터만 보이기

  const paginationElement = document.getElementById('pagination');
  paginationElement.innerHTML = '';

  if (totalPages > 1) {
    let pageNumbers = '';
    if (page !== 1) {
      pageNumbers += `<li class="page-item"><a class="page-link" href="#" data-page="${page - 1}">이전</a></li>`;
    }
    const pageBuffer = 2;
    for (let i = Math.max(1, page - pageBuffer); i <= Math.min(totalPages, page + pageBuffer); i++) {
      pageNumbers += `<li class="page-item${page === i ? ' active' : ''}"><a class="page-link" href="#" data-page="${i}">${i}</a></li>`;
    }
    if (page !== totalPages) {
      pageNumbers += `<li class="page-item"><a class="page-link" href="#" data-page="${page + 1}">다음</a></li>`;
    }
    paginationElement.innerHTML = pageNumbers;

    paginationElement.querySelectorAll('.page-link').forEach((item) => {
      item.addEventListener('click', (e) => {
        e.preventDefault();
        const currentPage = parseInt(e.target.dataset.page);
        if (inputBrand.trim().length === 0) {
            paginate(currentPage, '');
        } else {
            paginate(currentPage, inputBrand);
        }
      });
    });
  }
}






function fetchAndAddCPUBrandCategoryToTable() {
  axios.get('/cpu/brandCategory') // 서버의 /cpu 엔드포인트로 GET 요청을 보냅니다.
    .then(function (response) {
      // GET 요청이 성공하면 response.data에 서버에서 반환한 CPU 정보가 담겨 있습니다.
      var cpuInfos = response.data;

      // CPU 정보를 테이블에 추가합니다.
      var tableBody = document.getElementById('CPUBrandCategoryBody');
      for (var i = 0; i < cpuInfos.length; i++) {
        var cpuInfo = cpuInfos[i];
        var row = tableBody.insertRow(-1);
        row.insertCell(0).innerHTML =  '<div class="form-check"><input class="form-check-input" type="radio" name="cpuBrandCategoryRadios" id="brandRadio' + i + '"><label class="form-check-label" for="flexRadioDefault">'
                                            + cpuInfo +
                                          '</label>';

      }
    })
    .catch(function (error) {
      // GET 요청이 실패하면 에러 메시지를 출력합니다.
      console.error(error);
    });
}

function fetchAndAddCPUSocketCategoryToTable() {
  axios.get('/cpu/socketCategory') // 서버의 /cpu 엔드포인트로 GET 요청을 보냅니다.
    .then(function (response) {
      // GET 요청이 성공하면 response.data에 서버에서 반환한 CPU 정보가 담겨 있습니다.
      var cpuInfos = response.data;

      // CPU 정보를 테이블에 추가합니다.
      var tableBody = document.getElementById('CPUSocketCategoryBody');
      for (var i = 0; i < cpuInfos.length; i++) {
        var cpuInfo = cpuInfos[i];
        var row = tableBody.insertRow(-1);
        row.insertCell(0).innerHTML =  '<div class="form-check"><input class="form-check-input" type="radio" name="cpuSocketCategoryRadios" id="socketRadio' + i + '"><label class="form-check-label" for="flexRadioDefault">'
                                       + cpuInfo +
                                       '</label>';

      }
    })
    .catch(function (error) {
      // GET 요청이 실패하면 에러 메시지를 출력합니다.
      console.error(error);
    });
}

function filterByBrandCategory(page = 1, inputBrand) {
 axios.get(`/cpu/byBrand`, { params: { brand : inputBrand} })
    .then(function (response) {
      cpuInfos = response.data;
      paginate(1,'');
    })
    .catch(function (error) {
      console.error(error);
    });
    var cpuSocketCategoryRadios = document.querySelectorAll('#CPUSocketCategoryBody input[type="radio"]');
    cpuSocketCategoryRadios.forEach(function(radio) {
        radio.checked = false;
      });
}

function filterTable(page, checkedSocketCategory, columnIndex, inputText) {
  var tableBody = $('.table-list-search tbody');
  var tableRowsClass = $('.table-list-search tbody tr');
  var visibleRowCount = 0;
  $('.search-sf').remove();

  // 필터링된 결과 저장
  var filteredRows = [];
  tableRowsClass.each(function(i, val) {
    var rowText = $(val).find('td').eq(columnIndex).text().toLowerCase();
    var socketText = $(val).find('td').eq(4).text().toLowerCase();

    if (inputText && rowText.indexOf(inputText) === -1) {
      tableRowsClass.eq(i).hide();
    } else if (checkedSocketCategory && socketText.indexOf(checkedSocketCategory) === -1) {
      tableRowsClass.eq(i).hide();
    } else {
      tableRowsClass.eq(i).show();
      filteredRows.push(tableRowsClass.eq(i));
    }
  });

  // 검색 결과가 없을 경우 메시지 출력
  if (filteredRows.length === 0) {
    tableBody.append('<tr class="search-sf"><td class="text-muted" colspan="6">No entries found.</td></tr>');
  }

  // 페이지네이션 설정
  var pageCount = Math.ceil(filteredRows.length / pageSize);
  var startRow = (page - 1) * pageSize;
  var endRow = startRow + pageSize;
  var visibleRows = filteredRows.slice(startRow, endRow);

  // 표시할 행 숨기기
  tableRowsClass.hide();

  // 필터링된 결과에 해당하는 행만 보여주기
  visibleRows.forEach(function(val) {
    $(val).show();
  });

  const paginationElement = document.getElementById('pagination');
  paginationElement.innerHTML = '';

  if (pageCount > 1) {
    let pageNumbers = '';
    if (page !== 1) {
      pageNumbers += `<li class="page-item"><a class="page-link" href="#" data-page="${page - 1}">이전</a></li>`;
    }
    const pageBuffer = 2;
    for (let i = Math.max(1, page - pageBuffer); i <= Math.min(pageCount, page + pageBuffer); i++) {
      pageNumbers += `<li class="page-item${page === i ? ' active' : ''}"><a class="page-link" href="#" data-page="${i}">${i}</a></li>`;
    }
    if (page !== pageCount) {
      pageNumbers += `<li class="page-item"><a class="page-link" href="#" data-page="${page + 1}">다음</a></li>`;
    }
    paginationElement.innerHTML = pageNumbers;

    paginationElement.querySelectorAll('.page-link').forEach((item) => {
      item.addEventListener('click', (e) => {
        e.preventDefault();
        const currentPage = parseInt(e.target.dataset.page);
        filterTable(currentPage, checkedSocketCategory, columnIndex, inputText)
      });
    });
  }
}


// 초기화 함수
function clearAllFilters() {
  // 라디오 요소 가져오기
  var cpuSocketCategoryRadios = document.querySelectorAll('#CPUSocketCategoryBody input[type="radio"]');
  var cpuBrandCategoryRadios = document.querySelectorAll('#CPUBrandCategoryBody input[type="radio"]');

  //검색창 초기화
  document.getElementById("system-search").value = "";

  // 라디오 버튼 모두 선택 해제
  cpuSocketCategoryRadios.forEach(function(radio) {
    radio.checked = false;
  });

  cpuBrandCategoryRadios.forEach(function(radio) {
    radio.checked = false;
  });
  let tableBody = document.getElementById('CPUInfoTableBody');
  tableBody.innerHTML = '';

  checkedBrandCategory = undefined;
  checkedSocketCategory = undefined;

  fetchAndAddCPUsToTable();
}



// 페이지가 로드되면 CPU 정보를 가져와 테이블에 추가합니다.
window.onload = function () {
  var activeSystemClass = $('.list-group-item.active');
  var columnIndex = 0;
  var inputText = ''; // inputText 변수를 전역 변수로 선언

  // select 태그에 change 이벤트 핸들러 함수 등록
  var select = document.getElementById("category-select");
  select.addEventListener("change", function() {
    columnIndex = this.value;
    filterTable(1, checkedSocketCategory, columnIndex, inputText);
  });

  // 검색창에 입력할 때마다 테이블 필터링
  $('#system-search').keyup(function() {
    inputText = $(this).val().toLowerCase(); // inputText 변수에 할당
    filterTable(1, checkedSocketCategory, columnIndex, inputText);
  });

  fetchAndAddCPUsToTable();
  fetchAndAddCPUBrandCategoryToTable();
  fetchAndAddCPUSocketCategoryToTable();


  // 버튼 요소 가져오기
  var clearFiltersBtn = document.getElementById('clear-filters-btn');
  // 버튼에 클릭 이벤트 리스너 등록
  clearFiltersBtn.addEventListener('click', clearAllFilters);


  var columnIndex = 0;
  var select = document.getElementById("category-select");
  select.addEventListener("change", function() {
    columnIndex = this.value;
  });

  $(document).on('change', 'input[name=cpuBrandCategoryRadios]', function() {
    checkedBrandCategory = $('input[name=cpuBrandCategoryRadios]:checked').next('label').text();
    filterByBrandCategory(1, checkedBrandCategory);
  });


   $(document).on('change', 'input[name=cpuSocketCategoryRadios]', function() {
     checkedSocketCategory = $('input[name=cpuSocketCategoryRadios]:checked').next('label').text().toLowerCase();
     var inputValue = $('#system-search').val().toLowerCase();
     filterTable(1, checkedSocketCategory, columnIndex, inputText);
   });

};
