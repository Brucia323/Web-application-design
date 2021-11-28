"use strict";

var _interopRequireWildcard = require("@babel/runtime/helpers/interopRequireWildcard");

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.default = useKeyValueMapping;
exports.isDisabled = isDisabled;

var React = _interopRequireWildcard(require("react"));

function isDisabled(dataNode, skipType) {
  if (!dataNode) {
    return true;
  }

  var _dataNode$data$node = dataNode.data.node,
      disabled = _dataNode$data$node.disabled,
      disableCheckbox = _dataNode$data$node.disableCheckbox;

  switch (skipType) {
    case 'checkbox':
      return disabled || disableCheckbox;

    default:
      return disabled;
  }
}

function useKeyValueMapping(cacheKeyMap, cacheValueMap) {
  var getEntityByKey = React.useCallback(function (key) {
    var skipType = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : 'select';
    var ignoreDisabledCheck = arguments.length > 2 ? arguments[2] : undefined;
    var dataNode = cacheKeyMap.get(key);

    if (!ignoreDisabledCheck && isDisabled(dataNode, skipType)) {
      return null;
    }

    return dataNode;
  }, [cacheKeyMap]);
  var getEntityByValue = React.useCallback(function (value) {
    var skipType = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : 'select';
    var ignoreDisabledCheck = arguments.length > 2 ? arguments[2] : undefined;
    var dataNode = cacheValueMap.get(value);

    if (!ignoreDisabledCheck && isDisabled(dataNode, skipType)) {
      return null;
    }

    return dataNode;
  }, [cacheValueMap]);
  return [getEntityByKey, getEntityByValue];
}