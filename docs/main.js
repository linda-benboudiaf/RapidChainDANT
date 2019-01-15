/* var obj, dbParam, xmlhttp, myObj, x, txt = "";
var json = require('../data/pocket.js'); 
console.log(json); 

dbParam = JSON.stringify(json);
xmlhttp = new XMLHttpRequest();
xmlhttp.onreadystatechange = function() {
  if (this.readyState == 4 && this.status == 200) {
    myObj = JSON.parse(this.responseText);
    txt += "<table border='1'>"
    for (x in myObj) {
      txt += "<tr><td>" + myObj[x].name + "</td></tr>";
    }
    txt += "</table>"    
    document.getElementById("demo").innerHTML = txt;
  }
};
xmlhttp.open("POST", "json_demo_db_post.php", true);
xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
xmlhttp.send("x=" + dbParam); ***/


var json = require(pocket.json);  
console.log(json); 

/*const loadJSON = (callback) => {
    let xobj = new XMLHttpRequest();
    xobj.overrideMimeType("application/json");
    xobj.open('GET', '../data/pocket.json', true);
    // Replace 'my_data' with the path to your file
    xobj.onreadystatechange = () => {
        if (xobj.readyState === 4 && xobj.status === "200") {
            // Required use of an anonymous callback 
            // as .open() will NOT return a value but simply returns undefined in asynchronous mode
            callback(xobj.responseText);
        }
    };
    xobj.send(null);
}

const init = () => {
    loadJSON((response) => {
        // Parse JSON string into object
        let actual_JSON = JSON.parse(response);
    });
}*/