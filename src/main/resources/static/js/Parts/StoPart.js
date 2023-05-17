var checkedBrandCategory;
var checkedSocketCategory;
let stoInfos = [];
const pageSize = 10;
const MAX_NAME_BUNDLE = 5; //이름에서 꺼내올 문자열 개수
const MAX_STONAME_LENGTH = 100;

// 서버로부터 CPU 정보를 가Fuwbdiw져와 테이블에 추가하는 함수
function fetchAndAddSTOsToTable(page = 1, filter = '') {
  axios.get(`/sto`)
    .then(function (response) {
      stoInfos = response.data;
      paginate(1,'');
    })
    .catch(function (error) {
      console.error(error);
    });
}

async function inputToTableBody(row, tableBody, info) {
  if (info.stoName.length <= MAX_STONAME_LENGTH) {
    row.insertCell(0).innerHTML = `<a href="${info.amazon_Link}">${info.stoName}</a>`;
  } else {
    row.insertCell(0).innerHTML = `<a href="${info.amazon_Link}">${info.stoName.substring(0, MAX_STONAME_LENGTH) + '...'}</a>`;
  }
  row.insertCell(1).innerHTML = info.brand;
  row.insertCell(2).innerHTML = info.stoCapacity;
  row.insertCell(3).innerHTML = info.stoInterface;
  row.insertCell(4).innerHTML = info.stoType;

  const priceCell = row.insertCell(5);
  priceCell.innerHTML = '로딩 중...'; // 가격 정보가 로드되기 전에 표시할 텍스트

  row.insertCell(6).innerHTML = `<a href="${info.amazon_Link}"><img src="${info.amazon_img_link}" width="200" height="150"></a>`;
  row.insertCell(7).innerHTML = '<a class="btn btn-primary" href="/estimate?infoName=storage&info='+ info.stoName +'">추가</a>';

  const ItemInfo = await getData(info.stoName); // 가격 정보 가져오기
  const priceInfo = formatCurrency(ItemInfo.lowestPrice);
  const priceLink = ItemInfo.lowestPriceLink;
  priceCell.innerHTML = `<a href="${priceLink}">${priceInfo}</a>`; // 가격 정보 업데이트
}



function paginate(page,inputBrand) {
  const totalItems = stoInfos.length;
  const totalPages = Math.ceil(totalItems / pageSize);
  const startIndex = (page - 1) * pageSize;
  const endIndex = Math.min(startIndex + pageSize, totalItems);

  const tableBody = document.getElementById('STOInfoTableBody');
  tableBody.innerHTML = ''; // 테이블 초기화
  for (let i = startIndex; i < endIndex; i++) {
    // 테이블에 데이터 추가
    const stoInfo = stoInfos[i];
    const row = tableBody.insertRow(-1);
    inputToTableBody(row,tableBody,stoInfo)
  }

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






function fetchAndAddSTOBrandCategoryToTable() {
  axios.get('/sto/brandCategory') // 서버의 /cpu 엔드포인트로 GET 요청을 보냅니다.
    .then(function (response) {
      // GET 요청이 성공하면 response.data에 서버에서 반환한 CPU 정보가 담겨 있습니다.
      var stoInfos = response.data;

      // CPU 정보를 테이블에 추가합니다.
      var tableBody = document.getElementById('STOBrandCategoryBody');
      for (var i = 0; i < stoInfos.length; i++) {
        var stoInfo = stoInfos[i];
        var row = tableBody.insertRow(-1);
        row.insertCell(0).innerHTML =  '<div class="form-check"><input class="form-check-input" type="radio" name="cpuBrandCategoryRadios" id="brandRadio' + i + '"><label class="form-check-label" for="flexRadioDefault">'
                                            + stoInfo +
                                          '</label>';

      }
    })
    .catch(function (error) {
      // GET 요청이 실패하면 에러 메시지를 출력합니다.
      console.error(error);
    });
}

function fetchAndAddSTOTypeCategoryToTable() {
  axios.get('/sto/typeCategory') // 서버의 /cpu 엔드포인트로 GET 요청을 보냅니다.
    .then(function (response) {
      // GET 요청이 성공하면 response.data에 서버에서 반환한 CPU 정보가 담겨 있습니다.
      var stoInfos = response.data;

      // CPU 정보를 테이블에 추가합니다.
      var tableBody = document.getElementById('STOTypeCategoryBody');
      for (var i = 0; i < stoInfos.length; i++) {
        var stoInfo = stoInfos[i];
        var row = tableBody.insertRow(-1);
        row.insertCell(0).innerHTML =  '<div class="form-check"><input class="form-check-input" type="radio" name="cpuSocketCategoryRadios" id="socketRadio' + i + '"><label class="form-check-label" for="flexRadioDefault">'
                                       + stoInfo +
                                       '</label>';

      }
    })
    .catch(function (error) {
      // GET 요청이 실패하면 에러 메시지를 출력합니다.
      console.error(error);
    });
}

function filterByBrandCategory(page = 1, inputBrand) {
 axios.get(`/sto/byBrand`, { params: { brand : inputBrand} })
    .then(function (response) {
      stoInfos = response.data;
      paginate(1,'');
    })
    .catch(function (error) {
      console.error(error);
    });
    var stoSocketCategoryRadios = document.querySelectorAll('#STOTypeCategoryBody input[type="radio"]');
    stoSocketCategoryRadios.forEach(function(radio) {
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
  var stoTypeCategoryRadios = document.querySelectorAll('#STOTypeCategoryBody input[type="radio"]');
  var stoBrandCategoryRadios = document.querySelectorAll('#STOBrandCategoryBody input[type="radio"]');

  //검색창 초기화
  document.getElementById("system-search").value = "";

  // 라디오 버튼 모두 선택 해제
  stoTypeCategoryRadios.forEach(function(radio) {
    radio.checked = false;
  });

  stoBrandCategoryRadios.forEach(function(radio) {
    radio.checked = false;
  });
  let tableBody = document.getElementById('STOInfoTableBody');
  tableBody.innerHTML = '';

  checkedBrandCategory = undefined;
  checkedSocketCategory = undefined;

  fetchAndAddSTOsToTable();
}

function getData(searchText) {
  searchText = changeSearchText(searchText);

  function NaverItem(lowestPrice, lowestPriceLink) {
    this.lowestPrice = lowestPrice;
    this.lowestPriceLink = lowestPriceLink;
  }

  let itemInfo = new NaverItem(10000000, '#');

  return axios.get('/search/shop', {params: {query: searchText}})
    .then(response => {
      let items = response.data.items;
      items.forEach(item => {
        if (item.lprice < itemInfo.lowestPrice) {
          itemInfo.lowestPrice = item.lprice;
          itemInfo.lowestPriceLink = item.link;
        }
      });
      return itemInfo; // Promise 객체의 resolve 함수로 itemInfo 객체를 반환
    })
    .catch(error => {
      console.error(error);
      return itemInfo; // Promise 객체의 reject 함수로 itemInfo 객체를 반환
    });
}

function formatCurrency(amount) {
  if(amount == 10000000 ) {
        return "오류 발생"
  }
  const currency = new Intl.NumberFormat('ko-KR', { style: 'currency', currency: 'KRW' });
  return currency.format(amount);
}

function changeSearchText(str) {
  const words = str.split(' ');
  return words.slice(0, MAX_NAME_BUNDLE).join(' ');
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

  fetchAndAddSTOsToTable();
  fetchAndAddSTOBrandCategoryToTable();
  fetchAndAddSTOTypeCategoryToTable();


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
