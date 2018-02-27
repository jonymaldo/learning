variable name {
    description = "resource group name for packer"
}

provider "azurerm" {
    subscription_id = "${var.subscription_id}"
    client_id       = "${var.client_id}"
    client_secret   = "${var.client_secret}"
    tenant_id       = "${var.tenant_id}"
}

resource "azurerm_resource_group" "vm" {
    name = "${var.name}rg"
    location = "${var.location}"
}