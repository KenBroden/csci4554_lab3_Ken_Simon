
let str = "abcd";


// Converting a string to an array of ASCII decimal characters
let convertStringToAsciiDecimalCharacters = function(str){
  let l = str.length;
  let arr = [];
  for (let i = 0; i < l; i++) {
    arr[i] = str[i].charCodeAt(0);
  }
  return arr; 
}
  
const decimalArray = convertStringToAsciiDecimalCharacters(str);
console.log("The input string as an array of decimal characters is " + decimalArray)


let convertDecimalToAsciiBinaryCharacters = function(decimalArray){
  let binaryArray = [];
  for(let i = 0; i < decimalArray.length; i++){
    binaryArray[i] = decimalArray[i].toString(2).padStart(8, '0');
  }


  return binaryArray;
}

const binaryArray = convertDecimalToAsciiBinaryCharacters([2, 5, 32, 78])
console.log("The input string as an array of binary characters is " + binaryArray)

