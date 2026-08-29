const params = new URLSearchParams(window.location.search);
const id = params.get("id");

    console.log(id);

    fetch("/products/" + id)
        .then(response => response.json())
        .then(product => {
            const productName = document.getElementById("product-name");
            const productStock = document.getElementById("product-stock");

            productName.textContent = product.name;
            productStock.textContent = "在庫：" + product.stock;

            const editNameInput = document.getElementById("edit-name");
            const editCategoryInput = document.getElementById("edit-category");

            editNameInput.value = product.name;
            editCategoryInput.value = product.category;
        });

function loadHistory() {
    fetch("/product/" + id + "/history")
        .then(response => response.json())
        .then(histories => {
            const historyList = document.getElementById("history-list");

            historyList.innerHTML = "";

            histories.forEach(history => {
                const item = document.createElement("p");
                const date = new Date(history.createdAt);

                const formattedDate = date.toLocaleString("ja-JP", {
                        month: "numeric",
                        day: "numeric",
                        hour: "2-digit",
                        minute: "2-digit"
                    });

                const typeText = history.type === "IN" ? "入庫" : "出庫";

                item.textContent =
                    typeText + " " +
                    history.quantity + "個 " +
                    formattedDate;

                historyList.appendChild(item);
            });
        });
}
loadHistory();

const quantityInput = document.getElementById("quantity");
const addButton = document.getElementById("add-button");
const removeButton = document.getElementById("remove-button");

addButton.addEventListener("click", () => {
    const quantity = quantityInput.valueAsNumber;
    if(!Number.isInteger(quantity) || quantity < 1){
    message.textContent = "数量は1以上の整数を入力してください";
    return;
    }

    fetch("/products/" + id + "/stock/add?quantity=" + quantity, {
        method: "PUT"
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(errorData => {
                    throw new Error(errorData.message);
                });
            }

            return response.json();
        })
        .then(product => {
            const productStock = document.getElementById("product-stock");
            productStock.textContent = "在庫：" + product.stock;

            quantityInput.value = "";
            message.textContent = "";
            loadHistory();
        })
        .catch(error => {
            message.textContent = error.message;
        });
});

const message = document.getElementById("message");

removeButton.addEventListener("click", () => {
    const quantity = quantityInput.valueAsNumber;

    if (!Number.isInteger(quantity) || quantity < 1) {
        message.textContent = "数量は1以上の整数を入力してください";
        return;
    }

    fetch("/products/" + id + "/stock/remove?quantity=" + quantity, {
        method: "PUT"
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(errorData => {
                    throw new Error(errorData.message);
                });
            }

            return response.json();
        })
        .then(product => {
            const productStock = document.getElementById("product-stock");
            productStock.textContent = "在庫：" + product.stock;

            quantityInput.value = "";
            message.textContent = "";
            loadHistory();
        })
        .catch(error => {
            message.textContent = error.message;
        });
});

const deleteButton = document.getElementById("delete-button");

deleteButton.addEventListener("click", () => {
    const confirmed = window.confirm("この商品を削除しますか？");

    if (!confirmed) {
        return;
    }

    fetch("/products/" + id, {
        method: "DELETE"
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("削除できませんでした");
            }

            window.location.href = "/index.html";
        })
        .catch(error => {
            message.textContent = error.message;
        });
});

const editNameInput = document.getElementById("edit-name");
const editCategoryInput = document.getElementById("edit-category");
const editButton = document.getElementById("edit-button");

editButton.addEventListener("click", () => {
    const name = editNameInput.value.trim();
    const category = editCategoryInput.value;

    if(name === ""){
    message.textContent = "商品名を入力してください";
    return;
    }

    const updatedProduct = {
    name : name,
    category : category
    };

    fetch("/products/" + id,{
    method : "PUT",
    headers: {
        "Content-Type": "application/json"
    },
    body: JSON.stringify(updatedProduct)
    })

    .then(response => response.json())
    .then(product => {
    const productName = document.getElementById("product-name");
    productName.textContent = product.name;

    message.textContent = "更新しました"
    })
});