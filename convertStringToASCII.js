
let str = "%*#12";
console.log("INPUT STRING:'" + str + "'")

let key = "hello";
console.log("KEY:'" + key  + "'")

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
console.log("The input string as an array of decimal characters is ", decimalArray)

//Converting an array of decimal characters to binary characters
let convertDecimalToAsciiBinaryCharacters = function(decimalArray){
  let binaryArray = [];
  for(let i = 0; i < decimalArray.length; i++){
    let binaryString =  decimalArray[i].toString(2).padStart(8, '0');
    binaryArray[i] = binaryString;
  }
  return binaryArray;
}

const binaryArray = convertDecimalToAsciiBinaryCharacters(decimalArray)
console.log("The input string as an array of binary characters is", binaryArray)


