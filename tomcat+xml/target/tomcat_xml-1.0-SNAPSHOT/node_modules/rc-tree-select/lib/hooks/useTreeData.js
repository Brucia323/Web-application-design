"use strict";

var _interopRequireDefault = require("@babel/runtime/helpers/interopRequireDefault");

var _interopRequireWildcard = require("@babel/runtime/helpers/interopRequireWildcard");

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.default = useTreeData;

var _objectWithoutProperties2 = _interopRequireDefault(require("@babel/runtime/helpers/objectWithoutProperties"));

var _objectSpread2 = _interopRequireDefault(require("@babel/runtime/helpers/objectSpread2"));

var React = _interopRequireWildcard(require("react"));

var _warning = _interopRequireDefault(require("rc-util/lib/warning"));

var _legacyUtil = require("../utils/legacyUtil");

var _excluded = ["key", "children"];
var MAX_WARNING_TIMES = 10;

function parseSimpleTreeData(treeData, _ref) {
  var id = _ref.id,
      pId = _ref.pId,
      rootPId = _ref.rootPId;
  var keyNodes = {};
  var rootNodeList = []; // Fill in the map

  var nodeList = treeData.map(function (node) {
    var clone = (0, _objectSpread2.default)({}, node);
    var key = clone[id];
    keyNodes[key] = clone;
    clone.key = clone.key || key;
    return clone;
  }); // Connect tree

  nodeList.forEach(function (node) {
    var parentKey = node[pId];
    var parent = keyNodes[parentKey]; // Fill parent

    if (parent) {
      parent.children = parent.children || [];
      parent.children.push(node);
    } // Fill root tree node


    if (parentKey === rootPId || !parent && rootPId === null) {
      rootNodeList.push(node);
    }
  });
  return rootNodeList;
}
/**
 * Format `treeData` with `value` & `key` which is used for calculation
 */


function formatTreeData(treeData, getLabelProp, fieldNames) {
  var warningTimes = 0;
  var valueSet = new Set(); // Field names

  var fieldValue = fieldNames.value,
      fieldChildren = fieldNames.children;

  function dig(dataNodes) {
    return (dataNodes || []).map(function (node) {
      var key = node.key,
          children = node.children,
          restProps = (0, _objectWithoutProperties2.default)(node, _excluded);
      var value = node[fieldValue];
      var mergedValue = fieldValue in node ? value : key;
      var dataNode = (0, _objectSpread2.default)((0, _objectSpread2.default)({}, restProps), {}, {
        key: key !== null && key !== undefined ? key : mergedValue,
        value: mergedValue,
        title: getLabelProp(node),
        node: node
      }); // Check `key` & `value` and warning user

      if (process.env.NODE_ENV !== 'production') {
        if (key !== null && key !== undefined && value !== undefined && String(key) !== String(value) && warningTimes < MAX_WARNING_TIMES) {
          warningTimes += 1;
          (0, _warning.default)(false, "`key` or `value` with TreeNode must be the same or you can remove one of them. key: ".concat(key, ", value: ").concat(value, "."));
        }

        (0, _warning.default)(value !== undefined || key !== undefined, 'TreeNode `value` is invalidate: undefined');
        (0, _warning.default)(!valueSet.has(value), "Same `value` exist in the tree: ".concat(value));
        valueSet.add(value);
      }

      if (node[fieldChildren] !== undefined) {
        dataNode.children = dig(node[fieldChildren]);
      }

      return dataNode;
    });
  }

  return dig(treeData);
}
/**
 * Convert `treeData` or `children` into formatted `treeData`.
 * Will not re-calculate if `treeData` or `children` not change.
 */


function useTreeData(treeData, children, _ref2) {
  var getLabelProp = _ref2.getLabelProp,
      simpleMode = _ref2.simpleMode,
      fieldNames = _ref2.fieldNames;
  var cacheRef = React.useRef({});

  if (treeData) {
    cacheRef.current.formatTreeData = cacheRef.current.treeData === treeData ? cacheRef.current.formatTreeData : formatTreeData(simpleMode ? parseSimpleTreeData(treeData, (0, _objectSpread2.default)({
      id: 'id',
      pId: 'pId',
      rootPId: null
    }, simpleMode !== true ? simpleMode : {})) : treeData, getLabelProp, fieldNames);
    cacheRef.current.treeData = treeData;
  } else {
    cacheRef.current.formatTreeData = cacheRef.current.children === children ? cacheRef.current.formatTreeData : formatTreeData((0, _legacyUtil.convertChildrenToData)(children), getLabelProp, fieldNames);
  }

  return cacheRef.current.formatTreeData;
}