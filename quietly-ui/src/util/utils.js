const utils = {
    trimString: function(val) {
        if(val == null || val == undefined || val.trim() == '') {
            return null
        }
        return val.trim()
    }, 
    trimObjectValue: function(obj) {
        for (let item in obj) { 
            obj[item] = this.trimString(obj[item])
        }
        return obj
    }, 
    /**
     * @param {List} dataList 
     * @param {String or Number} rootId 0
     * @param {String} parentIdKeyName parentId
     * @param {String} idKeyName id
     * @param {String} nameKeyName name
     * @returns 
     */
    treeByKey: function(dataList, rootId, parentIdKeyName, idKeyName, nameKeyName) {
        let itemArray = []
        for(let i = 0; i < dataList.length; i++) {
            let node = dataList[i]
            if(node[parentIdKeyName] == rootId) {
                let newNode = { id: node[idKeyName], name: node[nameKeyName], children: this.tree(dataList, node[idKeyName]) }
                itemArray.push(newNode)
            }
        }
        return itemArray
    },
    /**
     * @params dataList List<Object>或者List<Map<String, Object>>数据
     * @params rootId 根值，树图或者级联数据最顶层的数据的id
     */
    tree: function (dataList, rootId) {
        let itemArray = []
        for(let i = 0; i < dataList.length; i++) {
            let node = dataList[i]
            if(node.parentId == rootId) {
                let newNode = { id: node.id, name: node.name, children: this.tree(dataList, node.id) }
                itemArray.push(newNode)
            }
        }
        return itemArray
    },
    stringToJson: function(string) {
        return JSON.parse(string)
    },
    jsonToString: function(json) {
        return JSON.stringify(json)
    },

}

export default utils