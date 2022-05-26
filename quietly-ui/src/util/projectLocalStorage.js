import {local, session} from './storage.js'

const projectIdKey = 'CURRENT_USER_ACTIVE_PROJECT_ID'

const projectLocalStorage = {
    setProjectId: function(id) {
        local.set(projectIdKey, id)
    },
    getProjectId: function() {
        return local.get(projectIdKey)
    },
    removeProjectId: function() {
        local.remove(projectIdKey)
    }
}

export default projectLocalStorage