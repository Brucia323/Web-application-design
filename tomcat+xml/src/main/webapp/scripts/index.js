/**
 * 打开页面检查登录状态，未登录就隐藏header
 */
const dakai = () => {
    if (sessionStorage.userId) {
        $('header').show(1000)
    } else {
        $('header').hide()
    }
}

/**
 * 登录按钮点击事件
 */
const login = () => {
    const $username = $('#login_username')
    const $password = $('#login_password')
    const username = $username.val()
    const password = $password.val()
    console.info({ username, password })
    /**
     * 账号是否合法
     */
    const regex1 = /^[a-zA-Z]\w{4,15}$/
    /**
     * 密码是否合法
     */
    const regex2 = /^[a-zA-Z]\w{5,17}$/
    const $loginUsernameError = $('#login_username_error')
    const $loginPasswordError = $('#login_password_error')
    if (!regex1.test(username)) {
        if (username == null || username == '') {
            $loginUsernameError.append('请输入账号！')
        } else {
            $loginUsernameError.append('账号不合法！')
        }
    } else if (!regex2.test(password)) {
        if (password == null || password == '') {
            $loginPasswordError.append('请输入密码！')
        } else {
            $loginPasswordError.append('密码不合法')
        }
    } else {
        $.post('UserServlet', { username, password, flag: 'login' }, (result) => {
            console.info(result)
            if (result != 0) {
                sessionStorage.userId = result
                $('#login_logup').hide(1000)
                dakai()
            } else {
                $('#login_error').show(1000)
            }
        })
    }
}

/**
 * 检查账号是否合法
 */
const checkUsername = () => {
    const $username = $('#logup_username')
    const username = $username.val()
    console.info(username)
    /**
     * 账号是否合法
     */
    const regex = /^[a-zA-Z]\w{4,15}$/
    const $logupUsernameError = $('#logup_username_error')
    if (regex.test(username)) {
        $.post('UserServlet', { username, flag: 'checkUsername' }, (result) => {
            if (result) {
                $logupUsernameError.empty()
                $logupUsernameError.append('✅')
                return true
            } else {
                $logupUsernameError.empty()
                $logupUsernameError.append('账号已被注册')
                return false
            }
        })
    } else {
        if (username == null || username == '') {
            $logupUsernameError.empty()
            $logupUsernameError.append('请输入账号！')
            return false
        } else {
            $logupUsernameError.empty()
            $logupUsernameError.append('账号不合法')
            return false
        }
    }
}

/**
 * 检测密码强度
 */
const checkPassword = () => {
    const $password = $('#logup_password')
    const password = $password.val()
    /**
     * 密码是否合法
     */
    const regex1 = /^[a-zA-Z]\w{5,17}$/
    /**
     * 强密码
     */
    const regex2 = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9]{8,10}$/
    const $logupPasswordError = $('#logup_password_error')
    if (regex1.test(password)) {
        if (regex2.test(password)) {
            $logupPasswordError.empty()
            $logupPasswordError.append('<fast-progress min="0" max="100" value="75"></fast-progress>')
        } else {
            $logupPasswordError.empty()
            $logupPasswordError.append('<fast-progress min="0" max="100" value="25"></fast-progress>')
        }
        return true
    } else {
        if (password == null || password == '') {
            $logupPasswordError.empty()
            $logupPasswordError.append('请输入密码！')
        } else {
            $logupPasswordError.empty()
            $logupPasswordError.append('密码不合法')
        }
        return false
    }
}

/**
 * 清空提示文字
 */
const clearError = (id) => {
    $('#' + id + '_error').empty()
}

/**
 * 关闭弹框
 */
const closeDialog = (id) => {
    $('#' + id).hide(1000)
}

/**
 * 点击注册按钮
 */
const logup = () => {
    if (checkPassword()) {
        const $username = $('#logup_username')
        const $password = $('#logup_password')
        const username = $username.val()
        const password = $password.val()
        $.post('UserServlet', { username, password, flag: 'logup' }, (result) => {
            if (result != 0) {
                console.info(result)
                sessionStorage.userId = result
                $('#login_logup').hide(1000)
                dakai()
            } else {
                $('#logup_error').show(1000)
            }
        })
    }
    else {
        $('#logup_error').show(1000);
    }
}

const tuichu = () => {
    sessionStorage.clear()
    $('#login_logup').show(1000)
    dakai()
}
