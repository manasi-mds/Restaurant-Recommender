import React from 'react';
import { Link } from '@cloudscape-design/components';
import { addColumnSortLabels } from '../commons/labels';

export const REST_DEFAULT_PREFERENCES = {
  pageSize: 30,
  visibleContent: ['name', 'address', 'cuisines', 'isAlcoholServed', 'rating','isOpen'],
  wrapLines: false,
};

export const REST_COLUMN_DEFINITIONS = addColumnSortLabels([
  {
    id: 'name',
    sortingField: 'name',
    header: 'Restaurant',
    cell: item => item.name,
    minWidth: 180,
  },
  {
    id: 'address',
    sortingField: 'address',
    header: 'Address',
    cell: item => item.address,
    minWidth: 100,
  },
  {
    id: 'cuisines',
    sortingField: 'cuisines',
    header: 'Cuisines',
    cell: item => item.cuisines,
    minWidth: 100,
  },
  {
    id: 'isAlcoholServed',
    sortingField: 'isAlcoholServed',
    header: 'Alcohol?',
    cell: item => item.isAlcoholServed,
    minWidth: 100,
  },
  {
    id: 'rating',
    sortingField: 'rating',
    header: 'Ratings',
    cell: item => item.rating,
    minWidth: 100,
  },
  {
    id: 'latitude',
    sortingField: 'latitude',
    header: 'Latitude',
    cell: item => item.latitude,
    minWidth: 100,
  },
  {
    id: 'longitude',
    sortingField: 'longitude',
    header: 'Longitude',
    cell: item => item.longitude,
    minWidth: 100,
  },
  {
    id: 'isOpen',
    sortingField: 'isOpen',
    header: 'Is Open?',
    cell: item => item.isOpen,
    minWidth: 100,
  }
]);

export const PAGE_SIZE_OPTIONS = [
  { value: 10, label: '10 Restaurants' },
  { value: 30, label: '30 Restaurants' },
  { value: 50, label: '50 Restaurants' },
];

export const REST_FILTERING_PROPERTIES = [
  {
    propertyLabel: 'Restaurant Name',
    key: 'name',
    groupValuesLabel: 'Restaurant Name values',
    operators: [':', '!:', '=', '!='],
  },
  {
    propertyLabel: 'Address',
    key: 'address',
    groupValuesLabel: 'Address values',
    operators: [':', '!:', '=', '!='],
  },
  {
    propertyLabel: 'Cuisines',
    key: 'cuisines',
    groupValuesLabel: 'Cuisine values',
    operators: [':', '!:', '=', '!='],
  },
  {
    propertyLabel: 'Alcohol',
    key: 'isAlcoholServed',
    groupValuesLabel: 'Alcohol values',
    operators: ['=', '!='],
  },
  {
    propertyLabel: 'Rating',
    key: 'rating',
    groupValuesLabel: 'Rating values',
    operators: ['>=', '>', '=', '!=','<','<='],
  },
  {
    propertyLabel: 'Open?',
    key: 'isOpen',
    groupValuesLabel: 'Open values',
    operators: ['=', '!='],
  }
];
