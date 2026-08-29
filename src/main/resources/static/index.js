fetch("/products")
        .then(response => response.json())
        .then(products => {
            products.forEach(product => {
            addProductToList(product);
            });
        });

const productNameInput = document.getElementById("product-name");
const productCategoryInput = document.getElementById("product-category");
const createButton = document.getElementById("create-button");
const message = document.getElementById("message");

createButton.addEventListener("click", () => {
    const name = productNameInput.value.trim();
    const category = productCategoryInput.value;

    if(name === ""){
        message.textContent = "商品名を入力してください";
        return;
    }

    const product = {
        name: name,
        category: category,
        stock: 0
    };

    fetch("/products", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"},
        body: JSON.stringify(product)
        })

        .then(response => response.json())
        .then(createdProduct => {
            addProductToList(createdProduct);

            productNameInput.value = "";
            productCategoryInput.value = "";
            message.textContent = "";
        });
});

function addProductToList(product) {
    const productList = document.getElementById("product-list");

    const item = document.createElement("a");
    item.className = "product-card";
    item.href = "/product.html?id=" + product.id;

    const name = document.createElement("span");
    name.className = "product-card-name";
    name.textContent = product.name;

    const stock = document.createElement("span");
    stock.className = "product-card-stock";
    stock.textContent = "在庫 " + product.stock;

    item.appendChild(name);
    item.appendChild(stock);

    productList.appendChild(item);
}