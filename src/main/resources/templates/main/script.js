var modal = document.getElementById("myModal");
var btn = document.getElementById("addPhotoBtn");
var span = document.getElementsByClassName("close")[0];

btn.onclick = function() {
  modal.style.display = "block";
}

span.onclick = function() {
  modal.style.display = "none";
}

window.onclick = function(event) {
  if (event.target == modal) {
    modal.style.display = "none";
  }
}


var weightModal = document.getElementById("weightModal");
var inputWeightBtn = document.getElementById("inputWeightBtn");
var closeWeightModal = document.getElementsByClassName("close")[0];


inputWeightBtn.onclick = function() {
  weightModal.style.display = "block";
}

closeWeightModal.onclick = function() {
  weightModal.style.display = "none";
}


window.onclick = function(event) {
  if (event.target == weightModal) {
    weightModal.style.display = "none";
  }
}

// Handle form submission
var weightForm = document.getElementById("weightForm");
weightForm.addEventListener("submit", function(event) {
    event.preventDefault(); // Prevent default form submission behavior
    var weightInput = document.getElementById("weightInput").value;
    // Do something with the weight value, like sending it to the server
    console.log("New weight: " + weightInput);
    weightModal.style.display = "none"; // Close the modal after submission
});

