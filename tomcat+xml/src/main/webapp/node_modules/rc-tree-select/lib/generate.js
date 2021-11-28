"use strict";

var _interopRequireWildcard = require("@babel/runtime/helpers/interopRequireWildcard");

var _interopRequireDefault = require("@babel/runtime/helpers/interopRequireDefault");

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.default = generate;

var _extends2 = _interopRequireDefault(require("@babel/runtime/helpers/extends"));

var _toConsumableArray2 = _interopRequireDefault(require("@babel/runtime/helpers/toConsumableArray"));

var _typeof2 = _interopRequireDefault(require("@babel/runtime/helpers/typeof"));

var _slicedToArray2 = _interopRequireDefault(require("@babel/runtime/helpers/slicedToArray"));

var React = _interopRequireWildcard(require("react"));

var _generate = _interopRequireDefault(require("rc-select/lib/generate"));

var _valueUtil = require("rc-select/lib/utils/valueUtil");

var _treeUtil = require("rc-tree/lib/utils/treeUtil");

var _conductUtil = require("rc-tree/lib/utils/conductUtil");

var _omit = _interopRequireDefault(require("rc-util/lib/omit"));

var _generator = require("rc-select/lib/interface/generator");

var _useMergedState3 = _interopRequireDefault(require("rc-util/lib/hooks/useMergedState"));

var _warning = _interopRequireDefault(require("rc-util/lib/warning"));

var _TreeNode = _interopRequireDefault(require("./TreeNode"));

var _valueUtil2 = require("./utils/valueUtil");

var _warningPropsUtil = _interopRequireDefault(require("./utils/warningPropsUtil"));

var _Context = require("./Context");

var _useTreeData = _interopRequireDefault(require("./hooks/useTreeData"));

var _useKeyValueMap3 = _interopRequireDefault(require("./hooks/useKeyValueMap"));

var _useKeyValueMapping3 = _interopRequireDefault(require("./hooks/useKeyValueMapping"));

var _strategyUtil = require("./utils/strategyUtil");

var _legacyUtil = require("./utils/legacyUtil");

var _useSelectValues = _interopRequireDefault(require("./hooks/useSelectValues"));

var OMIT_PROPS = ['expandedKeys', 'treeData', 'treeCheckable', 'showCheckedStrategy', 'searchPlaceholder', 'treeLine', 'treeIcon', 'showTreeIcon', 'switcherIcon', 'treeNodeFilterProp', 'filterTreeNode', 'dropdownPopupAlign', 'treeDefaultExpandAll', 'treeCheckStrictly', 'treeExpandedKeys', 'treeLoadedKeys', 'treeMotion', 'onTreeExpand', 'onTreeLoad', 'labelRender', 'loadData', 'treeDataSimpleMode', 'treeNodeLabelProp', 'treeDefaultExpandedKeys'];

function generate(config) {
  var prefixCls = config.prefixCls,
      optionList = config.optionList;
  var RefSelect = (0, _generate.default)({
    prefixCls: prefixCls,
    components: {
      optionList: optionList
    },
    // Not use generate since we will handle ourself
    convertChildrenToData: function convertChildrenToData() {
      return null;
    },
    flattenOptions: _valueUtil2.flattenOptions,
    // Handle `optionLabelProp` in TreeSelect component
    getLabeledValue: _valueUtil.getLabeledValue,
    filterOptions: _valueUtil2.filterOptions,
    isValueDisabled: _valueUtil2.isValueDisabled,
    findValueOption: _valueUtil2.findValueOption,
    omitDOMProps: function omitDOMProps(props) {
      return (0, _omit.default)(props, OMIT_PROPS);
    }
  });
  RefSelect.displayName = 'Select'; // =================================================================================
  // =                                      Tree                                     =
  // =================================================================================

  var RefTreeSelect = /*#__PURE__*/React.forwardRef(function (props, ref) {
    var fieldNames = props.fieldNames,
        multiple = props.multiple,
        treeCheckable = props.treeCheckable,
        treeCheckStrictly = props.treeCheckStrictly,
        _props$showCheckedStr = props.showCheckedStrategy,
        showCheckedStrategy = _props$showCheckedStr === void 0 ? 'SHOW_CHILD' : _props$showCheckedStr,
        labelInValue = props.labelInValue,
        loadData = props.loadData,
        treeLoadedKeys = props.treeLoadedKeys,
        _props$treeNodeFilter = props.treeNodeFilterProp,
        treeNodeFilterProp = _props$treeNodeFilter === void 0 ? 'value' : _props$treeNodeFilter,
        treeNodeLabelProp = props.treeNodeLabelProp,
        treeDataSimpleMode = props.treeDataSimpleMode,
        treeData = props.treeData,
        treeExpandedKeys = props.treeExpandedKeys,
        treeDefaultExpandedKeys = props.treeDefaultExpandedKeys,
        treeDefaultExpandAll = props.treeDefaultExpandAll,
        children = props.children,
        treeIcon = props.treeIcon,
        showTreeIcon = props.showTreeIcon,
        switcherIcon = props.switcherIcon,
        treeLine = props.treeLine,
        treeMotion = props.treeMotion,
        filterTreeNode = props.filterTreeNode,
        dropdownPopupAlign = props.dropdownPopupAlign,
        onChange = props.onChange,
        onTreeExpand = props.onTreeExpand,
        onTreeLoad = props.onTreeLoad,
        onDropdownVisibleChange = props.onDropdownVisibleChange,
        onSelect = props.onSelect,
        onDeselect = props.onDeselect,
        labelRender = props.labelRender;
    var mergedCheckable = treeCheckable || treeCheckStrictly;
    var mergedMultiple = multiple || mergedCheckable;
    var treeConduction = treeCheckable && !treeCheckStrictly;
    var mergedLabelInValue = treeCheckStrictly || labelInValue; // ======================= Tree Data =======================
    // FieldNames

    var mergedFieldNames = (0, _valueUtil2.fillFieldNames)(fieldNames, true); // Legacy both support `label` or `title` if not set.
    // We have to fallback to function to handle this

    var getTreeNodeTitle = function getTreeNodeTitle(node) {
      if (!treeData) {
        return node.title;
      }

      if (mergedFieldNames === null || mergedFieldNames === void 0 ? void 0 : mergedFieldNames.label) {
        return node[mergedFieldNames.label];
      }

      return node.label || node.title;
    };

    var getTreeNodeLabelProp = function getTreeNodeLabelProp(entity, val) {
      if (labelRender) {
        return labelRender(entity, val);
      } // Skip since entity not exist


      if (!entity) {
        return undefined;
      }

      var node = entity.data.node;

      if (treeNodeLabelProp) {
        return node[treeNodeLabelProp];
      }

      return getTreeNodeTitle(node);
    };

    var mergedTreeData = (0, _useTreeData.default)(treeData, children, {
      getLabelProp: getTreeNodeTitle,
      simpleMode: treeDataSimpleMode,
      fieldNames: mergedFieldNames
    });
    var flattedOptions = (0, React.useMemo)(function () {
      return (0, _valueUtil2.flattenOptions)(mergedTreeData);
    }, [mergedTreeData]);

    var _useKeyValueMap = (0, _useKeyValueMap3.default)(flattedOptions),
        _useKeyValueMap2 = (0, _slicedToArray2.default)(_useKeyValueMap, 2),
        cacheKeyMap = _useKeyValueMap2[0],
        cacheValueMap = _useKeyValueMap2[1];

    var _useKeyValueMapping = (0, _useKeyValueMapping3.default)(cacheKeyMap, cacheValueMap),
        _useKeyValueMapping2 = (0, _slicedToArray2.default)(_useKeyValueMapping, 2),
        getEntityByKey = _useKeyValueMapping2[0],
        getEntityByValue = _useKeyValueMapping2[1]; // Only generate keyEntities for check conduction when is `treeCheckable`


    var _useMemo = (0, React.useMemo)(function () {
      if (treeConduction) {
        return (0, _treeUtil.convertDataToEntities)(mergedTreeData);
      }

      return {
        keyEntities: null
      };
    }, [mergedTreeData, treeCheckable, treeCheckStrictly]),
        conductKeyEntities = _useMemo.keyEntities; // ========================== Ref ==========================


    var selectRef = React.useRef(null);
    React.useImperativeHandle(ref, function () {
      return {
        scrollTo: selectRef.current.scrollTo,
        focus: selectRef.current.focus,
        blur: selectRef.current.blur,

        /** @private Internal usage. It's save to remove if `rc-cascader` not use it any longer */
        getEntityByValue: getEntityByValue
      };
    }); // ========================= Value =========================

    var _useMergedState = (0, _useMergedState3.default)(props.defaultValue, {
      value: props.value
    }),
        _useMergedState2 = (0, _slicedToArray2.default)(_useMergedState, 2),
        value = _useMergedState2[0],
        setValue = _useMergedState2[1];
    /** Get `missingRawValues` which not exist in the tree yet */


    var splitRawValues = function splitRawValues(newRawValues) {
      var missingRawValues = [];
      var existRawValues = []; // Keep missing value in the cache

      newRawValues.forEach(function (val) {
        if (getEntityByValue(val)) {
          existRawValues.push(val);
        } else {
          missingRawValues.push(val);
        }
      });
      return {
        missingRawValues: missingRawValues,
        existRawValues: existRawValues
      };
    };

    var _useMemo2 = (0, React.useMemo)(function () {
      var valueHalfCheckedKeys = [];
      var newRawValues = [];
      (0, _valueUtil2.toArray)(value).forEach(function (item) {
        if (item && (0, _typeof2.default)(item) === 'object' && 'value' in item) {
          if (item.halfChecked && treeCheckStrictly) {
            var entity = getEntityByValue(item.value);
            valueHalfCheckedKeys.push(entity ? entity.key : item.value);
          } else {
            newRawValues.push(item.value);
          }
        } else {
          newRawValues.push(item);
        }
      }); // We need do conduction of values

      // We need do conduction of values
      if (treeConduction) {
        var _splitRawValues = splitRawValues(newRawValues),
            missingRawValues = _splitRawValues.missingRawValues,
            existRawValues = _splitRawValues.existRawValues;

        var keyList = existRawValues.map(function (val) {
          return getEntityByValue(val).key;
        });

        var _conductCheck = (0, _conductUtil.conductCheck)(keyList, true, conductKeyEntities),
            checkedKeys = _conductCheck.checkedKeys,
            halfCheckedKeys = _conductCheck.halfCheckedKeys;

        return [[].concat((0, _toConsumableArray2.default)(missingRawValues), (0, _toConsumableArray2.default)(checkedKeys.map(function (key) {
          return getEntityByKey(key).data.value;
        }))), halfCheckedKeys];
      }

      return [newRawValues, valueHalfCheckedKeys];
    }, [value, flattedOptions, mergedMultiple, mergedLabelInValue, treeCheckable, treeCheckStrictly]),
        _useMemo3 = (0, _slicedToArray2.default)(_useMemo2, 2),
        rawValues = _useMemo3[0],
        rawHalfCheckedKeys = _useMemo3[1];

    var selectValues = (0, _useSelectValues.default)(rawValues, {
      treeConduction: treeConduction,
      value: value,
      showCheckedStrategy: showCheckedStrategy,
      conductKeyEntities: conductKeyEntities,
      getEntityByValue: getEntityByValue,
      getEntityByKey: getEntityByKey,
      getLabelProp: getTreeNodeLabelProp
    });

    var triggerChange = function triggerChange(newRawValues, extra, source) {
      setValue(mergedMultiple ? newRawValues : newRawValues[0]);

      if (onChange) {
        var eventValues = newRawValues;

        if (treeConduction && showCheckedStrategy !== 'SHOW_ALL') {
          var keyList = newRawValues.map(function (val) {
            var entity = getEntityByValue(val);
            return entity ? entity.key : val;
          });
          var formattedKeyList = (0, _strategyUtil.formatStrategyKeys)(keyList, showCheckedStrategy, conductKeyEntities);
          eventValues = formattedKeyList.map(function (key) {
            var entity = getEntityByKey(key);
            return entity ? entity.data.value : key;
          });
        }

        var _ref = extra || {
          triggerValue: undefined,
          selected: undefined
        },
            triggerValue = _ref.triggerValue,
            selected = _ref.selected;

        var returnValues = mergedLabelInValue ? (0, _valueUtil2.getRawValueLabeled)(eventValues, value, getEntityByValue, getTreeNodeLabelProp) : eventValues; // We need fill half check back

        if (treeCheckStrictly) {
          var halfValues = rawHalfCheckedKeys.map(function (key) {
            var entity = getEntityByKey(key);
            return entity ? entity.data.value : key;
          }).filter(function (val) {
            return !eventValues.includes(val);
          });
          returnValues = [].concat((0, _toConsumableArray2.default)(returnValues), (0, _toConsumableArray2.default)((0, _valueUtil2.getRawValueLabeled)(halfValues, value, getEntityByValue, getTreeNodeLabelProp)));
        }

        var additionalInfo = {
          // [Legacy] Always return as array contains label & value
          preValue: selectValues,
          triggerValue: triggerValue
        }; // [Legacy] Fill legacy data if user query.
        // This is expansive that we only fill when user query
        // https://github.com/react-component/tree-select/blob/fe33eb7c27830c9ac70cd1fdb1ebbe7bc679c16a/src/Select.jsx

        var showPosition = true;

        if (treeCheckStrictly || source === 'selection' && !selected) {
          showPosition = false;
        }

        (0, _legacyUtil.fillAdditionalInfo)(additionalInfo, triggerValue, newRawValues, mergedTreeData, showPosition);

        if (mergedCheckable) {
          additionalInfo.checked = selected;
        } else {
          additionalInfo.selected = selected;
        }

        onChange(mergedMultiple ? returnValues : returnValues[0], mergedLabelInValue ? null : eventValues.map(function (val) {
          var entity = getEntityByValue(val);
          return entity ? entity.data.title : null;
        }), additionalInfo);
      }
    };

    var onInternalSelect = function onInternalSelect(selectValue, option, source) {
      var eventValue = mergedLabelInValue ? selectValue : selectValue;

      if (!mergedMultiple) {
        // Single mode always set value
        triggerChange([selectValue], {
          selected: true,
          triggerValue: selectValue
        }, source);
      } else {
        var newRawValues = (0, _valueUtil2.addValue)(rawValues, selectValue); // Add keys if tree conduction

        if (treeConduction) {
          // Should keep missing values
          var _splitRawValues2 = splitRawValues(newRawValues),
              missingRawValues = _splitRawValues2.missingRawValues,
              existRawValues = _splitRawValues2.existRawValues;

          var keyList = existRawValues.map(function (val) {
            return getEntityByValue(val).key;
          });

          var _conductCheck2 = (0, _conductUtil.conductCheck)(keyList, true, conductKeyEntities),
              checkedKeys = _conductCheck2.checkedKeys;

          newRawValues = [].concat((0, _toConsumableArray2.default)(missingRawValues), (0, _toConsumableArray2.default)(checkedKeys.map(function (key) {
            return getEntityByKey(key).data.value;
          })));
        }

        triggerChange(newRawValues, {
          selected: true,
          triggerValue: selectValue
        }, source);
      }

      if (onSelect) {
        onSelect(eventValue, option);
      }
    };

    var onInternalDeselect = function onInternalDeselect(selectValue, option, source) {
      var eventValue = mergedLabelInValue ? selectValue : selectValue;
      var newRawValues = (0, _valueUtil2.removeValue)(rawValues, selectValue); // Remove keys if tree conduction

      if (treeConduction) {
        var _splitRawValues3 = splitRawValues(newRawValues),
            missingRawValues = _splitRawValues3.missingRawValues,
            existRawValues = _splitRawValues3.existRawValues;

        var keyList = existRawValues.map(function (val) {
          return getEntityByValue(val).key;
        });

        var _conductCheck3 = (0, _conductUtil.conductCheck)(keyList, {
          checked: false,
          halfCheckedKeys: rawHalfCheckedKeys
        }, conductKeyEntities),
            checkedKeys = _conductCheck3.checkedKeys;

        newRawValues = [].concat((0, _toConsumableArray2.default)(missingRawValues), (0, _toConsumableArray2.default)(checkedKeys.map(function (key) {
          return getEntityByKey(key).data.value;
        })));
      }

      triggerChange(newRawValues, {
        selected: false,
        triggerValue: selectValue
      }, source);

      if (onDeselect) {
        onDeselect(eventValue, option);
      }
    };

    var onInternalClear = function onInternalClear() {
      triggerChange([], null, 'clear');
    }; // ========================= Open ==========================


    var onInternalDropdownVisibleChange = React.useCallback(function (open) {
      if (onDropdownVisibleChange) {
        var legacyParam = {};
        Object.defineProperty(legacyParam, 'documentClickClose', {
          get: function get() {
            (0, _warning.default)(false, 'Second param of `onDropdownVisibleChange` has been removed.');
            return false;
          }
        });
        onDropdownVisibleChange(open, legacyParam);
      }
    }, [onDropdownVisibleChange]); // ======================== Warning ========================

    if (process.env.NODE_ENV !== 'production') {
      (0, _warningPropsUtil.default)(props);
    } // ======================== Render =========================
    // We pass some props into select props style


    var selectProps = {
      optionLabelProp: null,
      optionFilterProp: treeNodeFilterProp,
      dropdownAlign: dropdownPopupAlign,
      internalProps: {
        mark: _generator.INTERNAL_PROPS_MARK,
        onClear: onInternalClear,
        skipTriggerChange: true,
        skipTriggerSelect: true,
        onRawSelect: onInternalSelect,
        onRawDeselect: onInternalDeselect
      }
    };

    if ('filterTreeNode' in props) {
      selectProps.filterOption = filterTreeNode;
    }

    var selectContext = React.useMemo(function () {
      return {
        checkable: mergedCheckable,
        loadData: loadData,
        treeLoadedKeys: treeLoadedKeys,
        onTreeLoad: onTreeLoad,
        checkedKeys: rawValues,
        halfCheckedKeys: rawHalfCheckedKeys,
        treeDefaultExpandAll: treeDefaultExpandAll,
        treeExpandedKeys: treeExpandedKeys,
        treeDefaultExpandedKeys: treeDefaultExpandedKeys,
        onTreeExpand: onTreeExpand,
        treeIcon: treeIcon,
        treeMotion: treeMotion,
        showTreeIcon: showTreeIcon,
        switcherIcon: switcherIcon,
        treeLine: treeLine,
        treeNodeFilterProp: treeNodeFilterProp,
        getEntityByKey: getEntityByKey,
        getEntityByValue: getEntityByValue
      };
    }, [mergedCheckable, loadData, treeLoadedKeys, onTreeLoad, rawValues, rawHalfCheckedKeys, treeDefaultExpandAll, treeExpandedKeys, treeDefaultExpandedKeys, onTreeExpand, treeIcon, treeMotion, showTreeIcon, switcherIcon, treeLine, treeNodeFilterProp, getEntityByKey, getEntityByValue]);
    return /*#__PURE__*/React.createElement(_Context.SelectContext.Provider, {
      value: selectContext
    }, /*#__PURE__*/React.createElement(RefSelect, (0, _extends2.default)({
      ref: selectRef,
      mode: mergedMultiple ? 'multiple' : null
    }, props, selectProps, {
      value: selectValues // We will handle this ourself since we need calculate conduction
      ,
      labelInValue: true,
      options: mergedTreeData,
      onChange: null,
      onSelect: null,
      onDeselect: null,
      onDropdownVisibleChange: onInternalDropdownVisibleChange
    })));
  });
  RefTreeSelect.displayName = 'TreeSelect'; // =================================================================================
  // =                                    Generic                                    =
  // =================================================================================

  var TreeSelect = RefTreeSelect;
  TreeSelect.TreeNode = _TreeNode.default;
  TreeSelect.SHOW_ALL = _strategyUtil.SHOW_ALL;
  TreeSelect.SHOW_PARENT = _strategyUtil.SHOW_PARENT;
  TreeSelect.SHOW_CHILD = _strategyUtil.SHOW_CHILD;
  return TreeSelect;
}