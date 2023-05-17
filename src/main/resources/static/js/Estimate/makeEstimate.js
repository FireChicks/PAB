function showCPUDropdown(partName) {
    var dropdownB = $('#cpuInfoB');
    var dropdownA = $('#cpuInfoA');
    var isShown = dropdownB.hasClass('show');

    if(isShown) {
        dropdownB.removeClass('show');
        dropdownA.removeClass('show');
        dropdownA.html('');
        dropdownB.html('');
    } else {
        axios.get('/cpu/getInfo', {
            params: {
                name: partName// gpuInfo로 변경
            }
        })
        .then(function(response) {
            var dropdownContentB = '<div class="dropdown-content">';
            dropdownContentB += '<hr>';
            dropdownContentB += '<p><b>brand</b> : ' + response.data.brand + '</p>';
            dropdownContentB += '<p><b>socket</b> : ' + response.data.cpu_socket + '</p>';
            dropdownContentB += '<p><b>model</b> : ' + response.data.cpuModel + '</p>';
            dropdownContentB += '</div>';
            dropdownB.html(dropdownContentB);

            var dropdownContentA = '<div class="dropdown-content">';
            dropdownContentA += '<a href="' + response.data.amazon_Link + '"><img src="' + response.data.amazon_img_link + '" width="150" height="150"></a>';
            dropdownContentA += '</div>';
            dropdownA.html(dropdownContentA);

            dropdownB.addClass('show');
            dropdownA.addClass('show');
        })
        .catch(function(error) {
            console.log(error);
        });
    }
}

function showMBDropdown(partName) {
    var dropdownB = $('#mbInfoB');
    var dropdownA = $('#mbInfoA');
    var isShown = dropdownB.hasClass('show');

    if(isShown) {
        dropdownB.removeClass('show');
        dropdownA.removeClass('show');
        dropdownA.html('');
        dropdownB.html('');
    } else {
        axios.get('/mb/getInfo', {
            params: {
                name: partName// gpuInfo로 변경
            }
        })
        .then(function(response) {
            var dropdownContentB = '<div class="dropdown-content">';
            dropdownContentB += '<hr>';
            dropdownContentB += '<p><b>brand</b> : ' + response.data.brand + '</p>';
            dropdownContentB += '<p><b>socket</b> : ' + response.data.mb_cpu_socket + '</p>';
            dropdownContentB += '<p><b>mem_gen</b> : ' + response.data.mbMemGen + '</p>';
            dropdownContentB += '</div>';
            dropdownB.html(dropdownContentB);

            var dropdownContentA = '<div class="dropdown-content">';
            dropdownContentA += '<a href="' + response.data.amazonLink + '"><img src="' + response.data.amazon_img_link + '" width="150" height="150"></a>';
            dropdownContentA += '</div>';
            dropdownA.html(dropdownContentA);

            dropdownB.addClass('show');
            dropdownA.addClass('show');
        })
        .catch(function(error) {
            console.log(error);
        });
    }
}

function showRAMDropdown(partName) {
    var dropdownB = $('#ramInfoB');
    var dropdownA = $('#ramInfoA');
    var isShown = dropdownB.hasClass('show');

    if(isShown) {
        dropdownB.removeClass('show');
        dropdownA.removeClass('show');
        dropdownA.html('');
        dropdownB.html('');
    } else {
        axios.get('/ram/getInfo', {
            params: {
                name: partName// gpuInfo로 변경
            }
        })
        .then(function(response) {
            var dropdownContentB = '<div class="dropdown-content">';
            dropdownContentB += '<hr>';
            dropdownContentB += '<p><b>brand</b> : ' + response.data.brand + '</p>';
            dropdownContentB += '<p><b>ram_gen</b> : ' + response.data.ramGen + '</p>';
            dropdownContentB += '<p><b>capacity</b> : ' + response.data.ramCapacity + '</p>';
            dropdownContentB += '</div>';
            dropdownB.html(dropdownContentB);

            var dropdownContentA = '<div class="dropdown-content">';
            dropdownContentA += '<a href="' + response.data.amazonLink + '"><img src="' + response.data.amazon_img_link + '" width="150" height="150"></a>';
            dropdownContentA += '</div>';
            dropdownA.html(dropdownContentA);

            dropdownB.addClass('show');
            dropdownA.addClass('show');
        })
        .catch(function(error) {
            console.log(error);
        });
    }
}

function showSTODropdown(partName) {
    var dropdownB = $('#stoInfoB');
    var dropdownA = $('#stoInfoA');
    var isShown = dropdownB.hasClass('show');

    if(isShown) {
        dropdownB.removeClass('show');
        dropdownA.removeClass('show');
        dropdownA.html('');
        dropdownB.html('');
    } else {
        axios.get('/sto/getInfo', {
            params: {
                name: partName// gpuInfo로 변경
            }
        })
        .then(function(response) {
            var dropdownContentB = '<div class="dropdown-content">';
            dropdownContentB += '<hr>';
            dropdownContentB += '<p><b>brand</b> : ' + response.data.brand + '</p>';
            dropdownContentB += '<p><b>sto_type</b> : ' + response.data.stoType + '</p>';
            dropdownContentB += '<p><b>capacity</b> : ' + response.data.stoCapacity + '</p>';
            dropdownContentB += '</div>';
            dropdownB.html(dropdownContentB);

            var dropdownContentA = '<div class="dropdown-content">';
            dropdownContentA += '<a href="' + response.data.amazon_Link + '"><img src="' + response.data.amazon_img_link + '" width="150" height="150"></a>';
            dropdownContentA += '</div>';
            dropdownA.html(dropdownContentA);

            dropdownB.addClass('show');
            dropdownA.addClass('show');
        })
        .catch(function(error) {
            console.log(error);
        });
    }
}

function showPOWDropdown(partName) {
    var dropdownB = $('#powInfoB');
    var dropdownA = $('#powInfoA');
    var isShown = dropdownB.hasClass('show');

    if(isShown) {
        dropdownB.removeClass('show');
        dropdownA.removeClass('show');
        dropdownA.html('');
        dropdownB.html('');
    } else {
        axios.get('/pow/getInfo', {
            params: {
                name: partName// gpuInfo로 변경
            }
        })
        .then(function(response) {
            var dropdownContentB = '<div class="dropdown-content">';
            dropdownContentB += '<hr>';
            dropdownContentB += '<p><b>brand</b> : ' + response.data.brand + '</p>';
            dropdownContentB += '<p><b>pow_pins</b> : ' + response.data.powPins + '</p>';
            dropdownContentB += '<p><b>watts</b> : ' + response.data.powWatts + '</p>';
            dropdownContentB += '</div>';
            dropdownB.html(dropdownContentB);

            var dropdownContentA = '<div class="dropdown-content">';
            dropdownContentA += '<a href="' + response.data.amazonLink + '"><img src="' + response.data.amazon_img_link + '" width="150" height="150"></a>';
            dropdownContentA += '</div>';
            dropdownA.html(dropdownContentA);

            dropdownB.addClass('show');
            dropdownA.addClass('show');
        })
        .catch(function(error) {
            console.log(error);
        });
    }
}

function showGPUDropdown(partName) {
    var dropdownB = $('#gpuInfoB');
    var dropdownA = $('#gpuInfoA');
    var isShown = dropdownB.hasClass('show');

    if(isShown) {
        dropdownB.removeClass('show');
        dropdownA.removeClass('show');
        dropdownA.html('');
        dropdownB.html('');
    } else {
        axios.get('/gpu/getInfo', {
            params: {
                name: partName// gpuInfo로 변경
            }
        })
        .then(function(response) {
            var dropdownContentB = '<div class="dropdown-content">';
            dropdownContentB += '<hr>';
            dropdownContentB += '<p><b>brand</b> : ' + response.data.brand + '</p>';
            dropdownContentB += '<p><b>RamCapacity</b> : ' + response.data.gpuRamCapacity + '</p>';
            dropdownContentB += '<p><b>gpuClock</b> : ' + response.data.gpuClock + '</p>';
            dropdownContentB += '</div>';
            dropdownB.html(dropdownContentB);

            var dropdownContentA = '<div class="dropdown-content">';
            dropdownContentA += '<a href="' + response.data.amazon_Link + '"><img src="' + response.data.amazon_img_link + '" width="150" height="150"></a>';
            dropdownContentA += '</div>';
            dropdownA.html(dropdownContentA);

            dropdownB.addClass('show');
            dropdownA.addClass('show');
        })
        .catch(function(error) {
            console.log(error);
        });
    }
}





