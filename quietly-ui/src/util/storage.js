export const local = {
    set: function(key, value) {
        window.localStorage.setItem(key, value)
    },
    get: function(key) {
        return window.localStorage.getItem(key)
    },
    remove: function(key) {
        window.localStorage.removeItem(key)
    },
    setJSON: function(key, value) {
        this.set(key, JSON.stringify(value))
    },
    getJSON: function(key) {
        return JSON.parse(this.get(key))
    }
}

export const session = {
    set: function(key, value) {
        window.sessionStorage.setItem(key, value)
    },
    get: function(key) {
        return window.sessionStorage.getItem(key)
    },
    remove: function(key) {
        window.sessionStorage.removeItem(key)
    },
    setJSON: function(key, value) {
        this.set(key, JSON.stringify(value))
    },
    getJSON: function(key) {
        return JSON.parse(this.get(key))
    }
}
