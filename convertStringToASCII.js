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

const keySplitBinaryArray = splitBinaryArray(keyBinaryArray);
console.log("The key binary array split into subarrays of 5 ASCII characters is ", keySplitBinaryArray); 


function encrypt(inputStringSplitBinaryArray, keySplitBinaryArray) {
  var encryptedBlocks = [];
  // for loop to iterate through the input string binary array
  for (var i = 0; i < inputStringSplitBinaryArray.length; i++) {
    var block = inputStringSplitBinaryArray[i];
    var shiftedBlock = circularRightShift(block, 3);
    var encryptedBlock = bitwiseXOR(shiftedBlock, keySplitBinaryArray[i]);
    encryptedBlocks.push(encryptedBlock);
  }
  return encryptedBlocks;
}

// shift the input string binary array to the right by 3
function circularRightShift(block, shift) {
  return block.slice(-shift).concat(block.slice(0, -shift));
}

// XOR the input string binary array with the key binary array
function bitwiseXOR(block, key) {
  return block.map(function(bit, index) {
    return bit ^ key[index];
  });
}

function decrypt(encryptedBlocks, keySplitBinaryArray) {
  var decryptedBlocks = [];
  // for loop to iterate through the encrypted blocks
  for (var i = 0; i < encryptedBlocks.length; i++) {
    var block = encryptedBlocks[i];
    var xorBlock = bitwiseXOR(block, keySplitBinaryArray[i]);
    var decryptedBlock = circularRightShift(xorBlock, 32); // 35 - 3 = 32
    decryptedBlocks.push(decryptedBlock);
  }
  return decryptedBlocks;
}

const encryptedBlocks = encrypt(inputStringSplitBinaryArray, keySplitBinaryArray);
console.log("Encrypted blocks:", encryptedBlocks);

const decryptedBlocks = decrypt(encryptedBlocks, keySplitBinaryArray);
console.log("Decrypted blocks:", decryptedBlocks);