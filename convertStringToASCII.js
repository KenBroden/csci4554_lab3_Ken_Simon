
// Converting a string to an array of ASCII decimal characters
let convertStringToAsciiDecimalCharacters = function(str){
  let l = str.length;
  let arr = [];
  for (let i = 0; i < l; i++) {
    arr = str[i].charCodeAt(0);
    console.log(arr);  
  }
}
   
let str = "abcd";
convertStringToAsciiDecimalCharacters(str);