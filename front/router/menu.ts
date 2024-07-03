export const menu =
    [
        {
            "name": "市场活动",
            "icon": "OfficeBuilding",
            "url": "/dashboard",
            "children": [
                {
                    name: "市场活动",
                    icon: "ShoppingTrolley",
                    url: "/dashboard/MarketActivity",
                },
            ]
        },
        {
            "name": "线索管理",
            "icon": "Magnet",
            "url": "/dashboard/clue",
        },
        {
            "name": "客户管理",
            "icon": "Watermelon",
            "url": "/dashboard/customer",
        },
        {
            "name": "交易管理",
            "icon": "CreditCard",
            "url": "/permission",
            "children": [
                {
                    name: "权限列表",
                    icon: "Permission",
                    url: "/permission/list",
                },
                {
                    name: "权限添加",
                    icon: "PermissionAdd",
                    url: "/permission/add",
                }
            ]
        },
        {
            name: "产品管理",
            icon: "Menu",
            url: "/menu",
        },
        {
            name: "字典管理",
            icon: "Notebook",
            url: "/dic",
        },
        {
            name: "用户管理",
            icon: "User",
            url: "/dashboard/userManage",
        },
        {
            name: "系统管理",
            icon: "Setting",
            url: "/setting",
        }
    ]