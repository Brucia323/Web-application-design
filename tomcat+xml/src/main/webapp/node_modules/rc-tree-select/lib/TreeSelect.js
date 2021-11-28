"use strict";

var _interopRequireDefault = require("@babel/runtime/helpers/interopRequireDefault");

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.default = void 0;

var _generate = _interopRequireDefault(require("./generate"));

var _OptionList = _interopRequireDefault(require("./OptionList"));

var TreeSelect = (0, _generate.default)({
  prefixCls: 'rc-tree-select',
  optionList: _OptionList.default
});
var _default = TreeSelect;
exports.default = _default;