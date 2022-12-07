import { StatusIndicator } from '@cloudscape-design/components';


export const StatusComponent = ({ status }) => {
    if (status === 'available') {
      return <StatusIndicator type="success">Available</StatusIndicator>;
    } else {
      return <StatusIndicator type="error">Unavailable</StatusIndicator>;
    }
  };