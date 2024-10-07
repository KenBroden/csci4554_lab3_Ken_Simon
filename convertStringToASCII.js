
let str = "abcd";


// Converting a string to an array of ASCII decimal characters
let convertStringToAsciiDecimalCharacters = function(str){
  let l = str.length;
  let arr = [];
  for (let i = 0; i < l; i++) {
    arr[i] = str[i].charCodeAt(0);
  }
  console.log(arr);  
}
   
const decimalASCIICharacters = convertStringToAsciiDecimalCharacters(str);

