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