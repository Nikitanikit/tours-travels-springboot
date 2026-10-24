document.addEventListener('DOMContentLoaded', () => {
  const toggle = document.querySelector('.mobile-toggle');
  const navigation = document.querySelector('#primary-navigation');

  const closeNavigation = () => {
    if (!toggle || !navigation) return;
    navigation.classList.remove('open');
    toggle.setAttribute('aria-expanded', 'false');
  };

  if (toggle && navigation) {
    toggle.addEventListener('click', () => {
      const isOpen = toggle.getAttribute('aria-expanded') === 'true';
      toggle.setAttribute('aria-expanded', String(!isOpen));
      navigation.classList.toggle('open', !isOpen);
    });
    navigation.querySelectorAll('a').forEach(link => link.addEventListener('click', closeNavigation));
    document.addEventListener('keydown', event => {
      if (event.key === 'Escape') closeNavigation();
    });
    document.addEventListener('click', event => {
      if (!navigation.contains(event.target) && !toggle.contains(event.target)) closeNavigation();
    });
  }

  document.querySelectorAll('input[type="date"]').forEach(input => {
    const today = new Date(); const offset = today.getTimezoneOffset();
    const local = new Date(today.getTime() - offset * 60000).toISOString().split('T')[0];
    if (!input.min) input.min = local;
  });
  document.querySelectorAll('input[name="startDate"]').forEach(startDate => {
    const endDate = startDate.form?.querySelector('input[name="endDate"]');
    if (!endDate) return;
    const updateEndDateMinimum = () => {
      endDate.min = startDate.value || startDate.min;
      if (endDate.value && endDate.value < endDate.min) endDate.value = endDate.min;
    };
    startDate.addEventListener('change', updateEndDateMinimum);
    updateEndDateMinimum();
  });
  document.querySelectorAll('[data-dismiss]').forEach(el => el.addEventListener('click', () => el.remove()));
});
