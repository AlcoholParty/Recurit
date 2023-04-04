function count(f){
    const staticPrice = parseInt(document.getElementById("staticPrice").value);
    let price = document.getElementById("price");
    let clickBtn = f;
    let num = document.getElementById("num");
    let numValue = Number(num.innerHTML);
    let resPriceNum = 0;
    if(clickBtn == "+"){
        numValue += 1;
        resPriceNum = staticPrice * numValue;
    }else{
        if(numValue > 1){
            numValue -= 1;
            resPriceNum = staticPrice * numValue;
        }else{
            alert("갯수는 1개 이하로 떨어질 수 없습니다.");
            resPriceNum = staticPrice * numValue;
        }
    }
    num.innerHTML = numValue;
    price.innerHTML = resPriceNum + "원";
}