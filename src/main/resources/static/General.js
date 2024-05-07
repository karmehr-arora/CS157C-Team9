function formatDate(inputDate) {
    const date = new Date(inputDate);

    const month = String(date.getMonth() + 1).padStart(2, '0')
    const day = String(date.getDate()).padStart(2, '0');
    const year = date.getFullYear();

    const formattedDate = `${month}/${day}/${year}`;
    return formattedDate;
}

function checkStatus(response){
    if(response.ok){
        return Promise.resolve(response);
    }else{
        return Promise.reject(response);
    }
}

function sortByCreatedAsc(objectsArray) {
    objectsArray.sort((a, b) => {
        const dateA = new Date(a.created);
        const dateB = new Date(b.created);

        // Compare the Date objects
        return dateA - dateB;
    });

    return objectsArray;
}

function sortByCreatedDesc(objectsArray) {
    objectsArray.sort((a, b) => {
        const dateA = new Date(a.created);
        const dateB = new Date(b.created);

        // Compare the Date objects
        return dateA - dateB;
    });

    objectsArray.reverse();

    // Return the reversed sorted array
    return objectsArray;
}

function postData(url, data) {
    // Retrieve the CSRF token from the meta tags
    let csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    let csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    // Create the fetch request headers and include the CSRF token
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    headers.append(csrfHeader, csrfToken);

    // Create the fetch options
    let options = {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(data)
    };

    // Perform the fetch request
    return fetch(url, options)
        .then(checkStatus)
        .then(response => response.json());
}

function postDataWithFile(url, formData) {
    // Retrieve the CSRF token from the meta tags
    let csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    let csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    // Create the fetch request headers and include the CSRF token
    let headers = new Headers();
    headers.append(csrfHeader, csrfToken);

    // Create the fetch options
    let options = {
        method: 'POST',
        headers: headers,
        body: formData
    };

    // Perform the fetch request
    return fetch(url, options)
        .then(checkStatus);
}

function postData(url, data) {
    // Retrieve the CSRF token from the meta tags
    let csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    let csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    // Create the fetch request headers and include the CSRF token
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    headers.append(csrfHeader, csrfToken);

    // Create the fetch options
    let options = {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(data)
    };

    // Perform the fetch request
    return fetch(url, options)
        .then(checkStatus)
        .then(response => response.json());
}