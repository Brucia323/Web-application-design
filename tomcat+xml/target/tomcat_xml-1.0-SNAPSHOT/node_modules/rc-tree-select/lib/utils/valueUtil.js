"use strict";

var _interopRequireDefault = require("@babel/runtime/helpers/interopRequireDefault");

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.addValue = addValue;
exports.fillFieldNames = fillFieldNames;
exports.filterOptions = filterOptions;
exports.findValueOption = findValueOption;
exports.flattenOptions = flattenOptions;
exports.getRawValueLabeled = getRawValueLabeled;
exports.isCheckDisabled = isCheckDisabled;
exports.isValueDisabled = isValueDisabled;
exports.removeValue = removeValue;
exports.toArray = toArray;

var _typeof2 = _interopRequireDefault(require("@babel/runtime/helpers/typeof"));

var _objectSpread2 = _interopRequireDefault(require("@babel/runtime/helpers/objectSpread2"));

var _treeUtil = require("rc-tree/lib/utils/treeUtil");

var _legacyUtil = require("./legacyUtil");

function toArray(value) {
  if (Array.isArray(value)) {
    return value;
  }

  return value !== undefined ? [value] : [];
}
/**
 * Fill `fieldNames` with default field names.
 *
 * @param fieldNames passed props
 * @param skipTitle Skip if no need fill `title`. This is useful since we have 2 name as same title level
 * @returns
 */


function fillFieldNames(fieldNames) {
  var skipTitle = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : false;

  var _ref = fieldNames || {},
      label = _ref.label,
      value = _ref.value,
      children = _ref.children;

  var filledNames = {
    value: value || 'value',
    children: children || 'children'
  };

  if (!skipTitle || label) {
    filledNames.label = label || 'label';
  }

  return filledNames;
}

function findValueOption(values, options) {
  var optionMap = new Map();
  options.forEach(function (flattenItem) {
    var data = flattenItem.data,
        value = flattenItem.value;
    optionMap.set(value, data.node);
  });
  return values.map(function (val) {
    return (0, _legacyUtil.fillLegacyProps)(optionMap.get(val));
  });
}

function isValueDisabled(value, options) {
  var option = findValueOption([value], options)[0];

  if (option) {
    return option.disabled;
  }

  return false;
}

function isCheckDisabled(node) {
  return node.disabled || node.disableCheckbox || node.checkable === false;
}

function getLevel(_ref2) {
  var parent = _ref2.parent;
  var level = 0;
  var current = parent;

  while (current) {
    current = current.parent;
    level += 1;
  }

  return level;
}
/**
 * Before reuse `rc-tree` logic, we need to add key since TreeSelect use `value` instead of `key`.
 */


function flattenOptions(options) {
  var typedOptions = options; // Add missing key

  function fillKey(list) {
    return (list || []).map(function (node) {
      var value = node.value,
          key = node.key,
          children = node.children;
      var clone = (0, _objectSpread2.default)((0, _objectSpread2.default)({}, node), {}, {
        key: 'key' in node ? key : value
      });

      if (children) {
        clone.children = fillKey(children);
      }

      return clone;
    });
  }

  var flattenList = (0, _treeUtil.flattenTreeData)(fillKey(typedOptions), true, null);
  var cacheMap = new Map();
  var flattenDateNodeList = flattenList.map(function (option) {
    var _option$parent;

    var data = option.data,
        key = option.key,
        value = option.value;
    var flattenNode = {
      key: key,
      value: value,
      data: data,
      level: getLevel(option),
      parentKey: (_option$parent = option.parent) === null || _option$parent === void 0 ? void 0 : _option$parent.data.key
    };
    cacheMap.set(key, flattenNode);
    return flattenNode;
  }); // Fill parent

  flattenDateNodeList.forEach(function (flattenNode) {
    // eslint-disable-next-line no-param-reassign
    flattenNode.parent = cacheMap.get(flattenNode.parentKey);
  });
  return flattenDateNodeList;
}

function getDefaultFilterOption(optionFilterProp) {
  return function (searchValue, dataNode) {
    var value = dataNode[optionFilterProp];
    return String(value).toLowerCase().includes(String(searchValue).toLowerCase());
  };
}
/** Filter options and return a new options by the search text */


function filterOptions(searchValue, options, _ref3) {
  var optionFilterProp = _ref3.optionFilterProp,
      filterOption = _ref3.filterOption;

  if (filterOption === false) {
    return options;
  }

  var filterOptionFunc;

  if (typeof filterOption === 'function') {
    filterOptionFunc = filterOption;
  } else {
    filterOptionFunc = getDefaultFilterOption(optionFilterProp);
  }

  function dig(list) {
    var keepAll = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : false;
    return list.map(function (dataNode) {
      var children = dataNode.children;
      var match = keepAll || filterOptionFunc(searchValue, (0, _legacyUtil.fillLegacyProps)(dataNode));
      var childList = dig(children || [], match);

      if (match || childList.length) {
        return (0, _objectSpread2.default)((0, _objectSpread2.default)({}, dataNode), {}, {
          children: childList
        });
      }

      return null;
    }).filter(function (node) {
      return node;
    });
  }

  return dig(options);
}

function getRawValueLabeled(values, prevValue, getEntityByValue, getLabelProp) {
  var valueMap = new Map();
  toArray(prevValue).forEach(function (item) {
    if (item && (0, _typeof2.default)(item) === 'object' && 'value' in item) {
      valueMap.set(item.value, item);
    }
  });
  return values.map(function (val) {
    var item = {
      value: val
    };
    var entity = getEntityByValue(val, 'select', true); // Always try to get the value by entity even it's enpty

    var label = getLabelProp(entity, val);

    if (label === undefined) {
      label = val;
    }

    if (valueMap.has(val)) {
      var labeledValue = valueMap.get(val);
      item.label = 'label' in labeledValue ? labeledValue.label : label;

      if ('halfChecked' in labeledValue) {
        item.halfChecked = labeledValue.halfChecked;
      }
    } else {
      item.label = label;
    }

    return item;
  });
}

function addValue(rawValues, value) {
  var values = new Set(rawValues);
  values.add(value);
  return Array.from(values);
}

function removeValue(rawValues, value) {
  var values = new Set(rawValues);
  values.delete(value);
  return Array.from(values);
}