// Đọc tệp JSON và hiển thị sản phẩm
fetch('products.json')
    .then(response => {
        if (!response.ok) throw new Error("Không thể tải sản phẩm");
        return response.json();
    })
    .then(products => {
        const productList = document.getElementById('product-list');
        products.forEach(product => {
            const productElement = document.createElement('div');
            productElement.classList.add('product');
            productElement.innerHTML = `
                <img src="${product.image}" alt="${product.name}" width="150">
                <h3>${product.name}</h3>
                <p>${product.description}</p>
                <p>${product.price} VND</p>
                <button onclick="addToCart(${product.id})">Thêm vào giỏ</button>
            `;
            productList.appendChild(productElement);
        });
    })
    .catch(error => console.error("Đã có lỗi:", error));

// Giỏ hàng
let cart = JSON.parse(localStorage.getItem('cart')) || [];

function addToCart(id) {
    fetch('products.json')
        .then(response => response.json())
        .then(products => {
            const product = products.find(p => p.id === id);
            if (product) {
                cart.push(product);
                localStorage.setItem('cart', JSON.stringify(cart));
                alert(`${product.name} đã được thêm vào giỏ hàng!`);
            }
        })
        .catch(error => console.error("Không thể thêm sản phẩm vào giỏ:", error));
}
