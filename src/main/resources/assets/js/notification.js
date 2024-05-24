class NotificationServiceImpl {
    showLoading(title = "Cargando", message = "Por favor, espere...") {
        Swal.fire({
            title: title,
            text: message,
            allowOutsideClick: false,
            didOpen: () => {
                Swal.showLoading();
            }
        });
    }

    showSuccessAlert(title = "Operación completada", message = undefined, closeCallback = () => {}) {
        Swal.fire({
            title: title,
            text: message,
            icon: "success",
            willClose: closeCallback
        });
    }

    hideLoading() {
        Swal.close()
    }

    showSuccessToast(message = "Operación completada") {
        this.showToast("success", 3000, message);
    }

    showWarningToast(message = "Se ha producido un error") {
        this.showToast("warning", 3000, message);
    }

    showToast(icon, timer, message) {
        Swal.mixin({
            toast: true,
            position: "top-end",
            showConfirmButton: false,
            timer: timer
        }).fire({
            icon: icon,
            title: message
        });
    }
}

const NotificationService = new NotificationServiceImpl();