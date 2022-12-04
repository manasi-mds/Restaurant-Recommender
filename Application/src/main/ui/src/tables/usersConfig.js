import React from 'react';
import { Link } from '@cloudscape-design/components';
import { addColumnSortLabels } from '../commons/labels';

export const USER_DEFAULT_PREFERENCES = {
  pageSize: 30,
  visibleContent: ['name'],
  wrapLines: false,
};

export const USER_COLUMN_DEFINITIONS = addColumnSortLabels([
  {
    id: 'name',
    sortingField: 'name',
    header: 'Name',
    cell: item => item.name,
    minWidth: 180,
  },
]);

export const PAGE_SIZE_OPTIONS = [
  { value: 10, label: '10 Users' },
  { value: 30, label: '30 Users' },
  { value: 50, label: '50 Users' },
];

export const USER_FILTERING_PROPERTIES = [
  {
    propertyLabel: 'User name',
    key: 'name',
    groupValuesLabel: 'User name values',
    operators: [':', '!:', '=', '!='],
  }
];

