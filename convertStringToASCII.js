let str = "hello2";
console.log("INPUT STRING:'" + str + "'")

let key = "a5Z#\t2";
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

const inputStringDecimalArray = convertStringToAsciiDecimalCharacters(str);
const keyDecimalArray = convertStringToAsciiDecimalCharacters(key)
console.log("The input string as an array of decimal characters is ", inputStringDecimalArray)
console.log("The key as an array of decimal characters is", keyDecimalArray)

//Converting an array of decimal characters to binary characters
let convertDecimalToAsciiBinaryCharacters = function(arr){
  let binaryArray = [];
  for(let i = 0; i < arr.length; i++){
    let binaryString =  arr[i].toString(2).padStart(8, '0');
    binaryArray[i] = binaryString;
  }
  return binaryArray;
}

const inputStringBinaryArray = convertDecimalToAsciiBinaryCharacters(inputStringDecimalArray)
const keyBinaryArray = convertDecimalToAsciiBinaryCharacters(keyDecimalArray)
console.log("The input string as an array of binary characters is", inputStringBinaryArray)
console.log("The key as an array of binary characters is", keyBinaryArray)


function splitBinaryArray(inputArray) {
  return inputArray.reduce((resultArray, item, index) => {
    const blockIndex = Math.floor(index / 5);
    if (!resultArray[blockIndex]) {
      resultArray[blockIndex] = []; 
    }
    resultArray[blockIndex].push(item);
    return resultArray;
  }, []);
}

const inputStringSplitBinaryArray = splitBinaryArray(inputStringBinaryArray);
console.log("The input string binary array split into subarrays of 5 ASCII characters is", inputStringSplitBinaryArray); 

let keySplitBinaryArray = splitBinaryArray(keyBinaryArray);
console.log("The key binary array split into subarrays of 5 ASCII characters is ", keySplitBinaryArray); 


function encrypt(inputStringSplitBinaryArray, keySplitBinaryArray){
  let shiftedInputStringSplitBinaryArray = inputStringSplitBinaryArray;
  for(let i = 0; i < inputStringSplitBinaryArray.length; i++){
    let lastThreeElements = [];
    lastThreeElements += ((inputStringSplitBinaryArray[i][(inputStringSplitBinaryArray[i]).length - 1]).split()[0]).slice(-3);
    shiftedInputStringSplitBinaryArray[i].unshift(lastThreeElements);
    shiftedInputStringSplitBinaryArray[i][(inputStringSplitBinaryArray[i]).length - 1] = shiftedInputStringSplitBinaryArray[i][(inputStringSplitBinaryArray[i]).length - 1].slice(0, -3);
  }
  let shiftedArray = shiftedInputStringSplitBinaryArray;
  let shiftedString = (shiftedArray.toString()).replace(/,/g, "")
  let keyString = keySplitBinaryArray.toString().replace(/,/g, "")
  console.log(keyString)
  console.log(shiftedString)


  let result = "";



  for (let i = 0; i < shiftedString.length; i++) {
    const charCode1 = shiftedString.charCodeAt(i);
    const charCode2 = keyString.charCodeAt(i);
    result += String.fromCharCode(charCode1 ^ charCode2);
  }

  return result;
  


}


console.log(encrypt(inputStringSplitBinaryArray, keySplitBinaryArray))

